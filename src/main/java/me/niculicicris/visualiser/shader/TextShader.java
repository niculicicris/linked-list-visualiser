package me.niculicicris.visualiser.shader;

import me.niculicicris.visualiser.component.position.CameraPosition;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.font.CharacterInfo;
import me.niculicicris.visualiser.font.Font;
import me.niculicicris.visualiser.font.FontManager;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TextShader extends Shader {
    private static final String VERTEX_SHADER_PATH = "shader/text/TextVertexShader.glsl";
    private static final String FRAGMENT_SHADER_PATH = "shader/text/TextFragmentShader.glsl";

    private final CameraPosition cameraPosition;
    private Position textPosition;
    private final Font font;
    private String text;
    private boolean fixed;

    private int vaoId;
    private int vboId;
    private int eboId;

    public TextShader(CameraPosition cameraPosition, Position textPosition, String text) {
        super(VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);

        this.cameraPosition = cameraPosition;
        this.textPosition = textPosition;
        this.font = FontManager.getInstance().getFont();
        this.text = text;
        this.fixed = false;

        updateBufferSize();
    }

    @Override
    protected void createVertexArrayObject() {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
    }

    @Override
    protected void createVertexBufferObject() {
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
    }

    @Override
    protected void createElementBufferObject() {
        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
    }

    @Override
    protected void createAttributePointers() {
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 20, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 12);
    }

    @Override
    public void use() {
        updateData();
        prepare();
        draw();
        cleanup();
    }

    private void updateData() {
        updateVertexBufferData();
        updateElementBufferData();
    }

    private void updateVertexBufferData() {
        FloatBuffer vertexData = createVertexBufferData();

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexData);
    }

    private FloatBuffer createVertexBufferData() {
        FloatBuffer vertexData = BufferUtils.createFloatBuffer(20 * text.length());
        float[] vertices = new float[20 * text.length()];

        int index = 0, offset = 0;
        for (char character : text.toCharArray()) {
            CharacterInfo info = font.getCharacterInfo(character);

            addLeftDownVertex(vertices, index, info, offset);
            index += 5;

            addRightDownVertex(vertices, index, info, offset);
            index += 5;

            addRightTopVertex(vertices, index, info, offset);
            index += 5;

            addLeftTopVertex(vertices, index, info, offset);
            index += 5;

            offset += info.getXAdvance();
        }
        vertexData.put(vertices).flip();

        return vertexData;
    }

    private void addLeftDownVertex(float[] vertices, int index, CharacterInfo info, int offset) {
        vertices[index] = textPosition.getX() + info.getXOffset() + offset;
        vertices[index + 1] = textPosition.getY() + font.getBase() - info.getHeight() - info.getYOffset();
        vertices[index + 2] = 0;
        vertices[index + 3] = info.getLeftDownPoint().x;
        vertices[index + 4] = info.getLeftDownPoint().y;
    }

    private void addRightDownVertex(float[] vertices, int index, CharacterInfo info, int offset) {
        vertices[index] = textPosition.getX() + info.getWidth() + info.getXOffset() + offset;
        vertices[index + 1] = textPosition.getY() + font.getBase() - info.getHeight() - info.getYOffset();
        vertices[index + 2] = 0;
        vertices[index + 3] = info.getRightDownPoint().x;
        vertices[index + 4] = info.getRightDownPoint().y;
    }

    private void addRightTopVertex(float[] vertices, int index, CharacterInfo info, int offset) {
        vertices[index] = textPosition.getX() + info.getWidth() + info.getXOffset() + offset;
        vertices[index + 1] = textPosition.getY() + font.getBase() - info.getYOffset();
        vertices[index + 2] = 0;
        vertices[index + 3] = info.getRightTopPoint().x;
        vertices[index + 4] = info.getRightTopPoint().y;
    }

    private void addLeftTopVertex(float[] vertices, int index, CharacterInfo info, int offset) {
        vertices[index] = textPosition.getX() + info.getXOffset() + offset;
        vertices[index + 1] = textPosition.getY() + font.getBase() - info.getYOffset();
        vertices[index + 2] = 0;
        vertices[index + 3] = info.getLeftTopPoint().x;
        vertices[index + 4] = info.getLeftTopPoint().y;
    }

    private void updateElementBufferData() {
        IntBuffer elementData = createElementBufferData();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, elementData);
    }

    private IntBuffer createElementBufferData() {
        IntBuffer elementData = BufferUtils.createIntBuffer(6 * text.length());
        int[] elements = new int[6 * text.length()];

        int length = text.length(), index = 0, value = 0;
        while (length > 0) {
            elements[index] = value;
            elements[index + 1] = value + 1;
            elements[index + 2] = value + 2;
            elements[index + 3] = value;
            elements[index + 4] = value + 2;
            elements[index + 5] = value + 3;

            index += 6;
            value += 4;

            length--;
        }
        elementData.put(elements).flip();

        return elementData;
    }

    private void prepare() {
        attach();
        uploadUniforms();
        uploadTexture();
        enableVertexAttribArrays();
    }

    private void attach() {
        glUseProgram(getProgramId());
        glBindVertexArray(vaoId);
    }

    private void uploadUniforms() {
        uploadUniformMatrix("uProjection", cameraPosition.getProjection());

        if (fixed) {
            uploadUniformMatrix("uView", cameraPosition.getIdentity());
        } else {
            uploadUniformMatrix("uView", cameraPosition.getView());
        }
    }

    private void uploadTexture() {
        uploadTexture("textureSampler", 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, font.getTextureId());
    }

    private void enableVertexAttribArrays() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
    }

    private void draw() {
        glDrawElements(GL_TRIANGLES, 6 * text.length(), GL_UNSIGNED_INT, 0);
    }

    private void cleanup() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glUseProgram(0);
    }

    public void setTextPosition(Position textPosition) {
        this.textPosition = textPosition;
    }

    public void setText(String text) {
        if (this.text.length() != text.length()) {
            this.text = text;
            updateBufferSize();
        }

        this.text = text;
    }

    private void updateBufferSize() {
        attachVertexArrayObject();
        updateVertexBufferSize();
        updateElementBufferSize();
        detachVertexArrayObject();
    }

    private void attachVertexArrayObject() {
        glBindVertexArray(vaoId);
    }

    private void updateVertexBufferSize() {
        FloatBuffer newBuffer = BufferUtils.createFloatBuffer(20 * text.length());

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, newBuffer, GL_DYNAMIC_DRAW);
    }

    private void updateElementBufferSize() {
        IntBuffer newBuffer = BufferUtils.createIntBuffer(6 * text.length());

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, newBuffer, GL_DYNAMIC_DRAW);
    }

    private void detachVertexArrayObject() {
        glBindVertexArray(0);
    }


    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
