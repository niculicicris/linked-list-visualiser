package me.niculicicris.visualiser.font;

public class FontManager {
    private static final String FONT_PATH = "font/roboto.fnt";
    private static final String FONT_ATLAS = "texture/roboto.png";

    private static FontManager instance;
    private Font font;

    private FontManager() {}

    public static FontManager getInstance() {
        if (instance == null) {
            instance = new FontManager();
        }

        return instance;
    }

    public void initialize() {
        font = new Font(FONT_PATH, FONT_ATLAS);
    }

    public Font getFont() {
        return font;
    }
}
