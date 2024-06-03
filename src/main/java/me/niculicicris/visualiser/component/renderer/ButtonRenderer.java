package me.niculicicris.visualiser.component.renderer;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.Setup;
import me.niculicicris.visualiser.component.position.CameraPosition;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.font.Font;
import me.niculicicris.visualiser.font.FontManager;
import me.niculicicris.visualiser.shader.BorderShader;
import me.niculicicris.visualiser.shader.TextShader;

public class ButtonRenderer implements Setup, Renderer {
    private final String parentName;
    private final String label;
    private CameraPosition cameraPosition;
    private Position position;
    private TextShader textShader;
    private BorderShader borderShader;

    public ButtonRenderer(String parentName, String label) {
        this.parentName = parentName;
        this.label = label;
    }

    @Override
    public void setup() {
        Font font = FontManager.getInstance().getFont();
        float width = font.calculateWidth(label);
        float height = font.calculateHeight(label);

        this.cameraPosition = (CameraPosition) Component.getComponent("camera", "position");
        this.position = (Position) Component.getComponent(parentName, "position");
        this.textShader = new TextShader(cameraPosition, position, label);
        this.borderShader = new BorderShader(cameraPosition, position, width, height);

        this.textShader.setFixed(true);
        this.borderShader.setFixed(true);
    }

    @Override
    public void render() {
        textShader.use();
        borderShader.use();
    }
}
