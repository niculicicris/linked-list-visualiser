package me.niculicicris.visualiser.font;

import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Font {
    private final String fontPath;
    private final String texturePath;
    private Iterator<String> lines;
    private int textureWidth, textureHeight;
    private int base;
    private final Map<Character, CharacterInfo> characterInfo;
    private int textureId;

    public Font(String fontPath, String texturePath) {
        this.fontPath = fontPath;
        this.texturePath = texturePath;
        this.characterInfo = new HashMap<>();

        loadFont();
    }

    private void loadFont() {
        loadFontInformation();
        loadFontAtlas();
    }

    private void loadFontInformation() {
        initializeIterator();
        skipLine();
        loadMetrics();
        skipLine();
        loadCharactersInfo();
    }

    private void skipLine() {
        lines.next();
    }

    private void loadMetrics() {
        Map<String, String> values = processLine();

        textureWidth = convertToInteger(values.get("scaleW"));
        textureHeight = convertToInteger(values.get("scaleH"));
        base = convertToInteger(values.get("base"));
    }

    private void loadCharactersInfo() {
        int numberOfCharacters = loadNumberOfCharacters();

        while (numberOfCharacters > 0) {
            loadCharacterInfo();
            numberOfCharacters--;
        }
    }

    private void loadCharacterInfo() {
        Map<String, String> values = processLine();
        Character id = (char) Integer.parseInt(values.get("id"));
        CharacterInfo info = createCharacterInfo(values);

        characterInfo.put(id, info);
    }

    private CharacterInfo createCharacterInfo(Map<String, String> values) {
        int x = convertToInteger(values.get("x"));
        int y = convertToInteger(values.get("y"));
        int width = convertToInteger(values.get("width"));
        int height = convertToInteger(values.get("height"));
        int xOffset = convertToInteger(values.get("xoffset"));
        int yOffset = convertToInteger(values.get("yoffset"));
        int xAdvance = convertToInteger(values.get("xadvance"));

        CharacterInfo info = new CharacterInfo(x, y, width, height, xOffset, yOffset, xAdvance);
        info.normalize(textureWidth, textureHeight);

        return info;
    }

    private int loadNumberOfCharacters() {
        Map<String, String> values = processLine();
        return convertToInteger(values.get("count"));
    }

    private int convertToInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Could not convert '" + value + "' to an integer.");
        }
    }

    private Map<String, String> processLine() {
        Map<String, String> values = new HashMap<>();
        String[] lineWords = lines.next().split(" ");

        for (String word : lineWords) {
            String[] wordParts = word.split("=");

            if (wordParts.length == 2) {
                values.put(wordParts[0], wordParts[1]);
            }
        }

        return values;
    }

    private void initializeIterator() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream fileStream = classLoader.getResourceAsStream(fontPath);

        if (fileStream == null) {
            throw new IllegalStateException("Failed to load font.");
        }

        try (InputStreamReader fileStreamReader = new InputStreamReader(fileStream);
             BufferedReader reader = new BufferedReader(fileStreamReader)) {
            lines = reader.lines().toList().iterator();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load font.");
        }
    }

    private void loadFontAtlas() {
        generateTexture();
        setTextureParameters();
        loadTexture();
    }

    private void generateTexture() {
        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    private void setTextureParameters() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    private void loadTexture() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer image = stbi_load(getTexturePath(), width, height, channels, 0);

        if (image == null) {
            throw new IllegalStateException("Failed to load font.");
        }

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        stbi_image_free(image);
    }

    private String getTexturePath() {
        URL resourceUrl = getClass().getClassLoader().getResource(texturePath);

        if (resourceUrl == null) {
            throw new IllegalStateException("Failed to load font.");
        }

        return resourceUrl.getPath();
    }

    public int getBase() {
        return base;
    }

    public CharacterInfo getCharacterInfo(char character) {
        return characterInfo.get(character);
    }

    public int getTextureId() {
        return textureId;
    }

    public float calculateWidth(String text) {
        float width = 0;

        for (char character : text.toCharArray()) {
            CharacterInfo info = getCharacterInfo(character);
            width += info.getXAdvance();
        }

        return width;
    }

    public float calculateHeight(String text) {
        float height = 0;

        for (char character : text.toCharArray()) {
            CharacterInfo info = getCharacterInfo(character);
            if (info.getHeight() > height) {
                height = info.getHeight();
            }
        }

        return height;
    }
}
