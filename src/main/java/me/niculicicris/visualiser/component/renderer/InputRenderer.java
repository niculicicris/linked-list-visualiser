package me.niculicicris.visualiser.component.renderer;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.Setup;
import me.niculicicris.visualiser.component.input.InputData;
import me.niculicicris.visualiser.component.position.CameraPosition;
import me.niculicicris.visualiser.component.position.EntityPosition;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.font.Font;
import me.niculicicris.visualiser.font.FontManager;
import me.niculicicris.visualiser.shader.BorderShader;
import me.niculicicris.visualiser.shader.TextShader;

public class InputRenderer implements Setup, Renderer {
    private final String parentName;
    private CameraPosition cameraPosition;
    private Position position;
    private Position titlePosition;
    private Position textPosition;
    private InputData data;
    private TextShader textShader;
    private BorderShader borderShader;
    private Font font;
    private float width;
    private float height;

    public InputRenderer(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public void setup() {
        this.font = FontManager.getInstance().getFont();
        this.width = font.calculateWidth("12345678");
        this.height = font.calculateHeight("12345678");
        this.cameraPosition = (CameraPosition) Component.getComponent("camera", "position");
        this.position = (Position) Component.getComponent(parentName, "position");
        this.titlePosition = new EntityPosition(position);
        this.textPosition = new EntityPosition(position);
        this.data = (InputData) Component.getComponent(parentName, "data");
        this.textShader = new TextShader(cameraPosition, position, "");
        this.borderShader = new BorderShader(cameraPosition, position, width, height);

        this.position.setX((1920.0f - width) / 2);
        this.titlePosition.setX((1920.0f - width) / 2 - width / 4);
        this.titlePosition.addY(height + 64.0f);
        this.textShader.setFixed(true);
        this.borderShader.setFixed(true);
    }

    @Override
    public void render() {
        borderShader.use();

        textShader.setTextPosition(titlePosition);
        textShader.setText("Enter a number");
        textShader.use();

        float textWidth = font.calculateWidth(data.getStringValue());
        textPosition.setX((1920.0f - textWidth) / 2);
        textShader.setTextPosition(textPosition);
        textShader.setText(data.getStringValue());
        textShader.use();
    }
}
