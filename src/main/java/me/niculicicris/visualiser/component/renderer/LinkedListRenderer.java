package me.niculicicris.visualiser.component.renderer;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.list.LinkedListData;
import me.niculicicris.visualiser.component.Setup;
import me.niculicicris.visualiser.component.position.CameraPosition;
import me.niculicicris.visualiser.component.position.EntityPosition;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.font.Font;
import me.niculicicris.visualiser.font.FontManager;
import me.niculicicris.visualiser.shader.ArrowShader;
import me.niculicicris.visualiser.shader.BorderShader;
import me.niculicicris.visualiser.shader.TextShader;

public class LinkedListRenderer implements Setup, Renderer {
    private CameraPosition cameraPosition;
    private Position linkedListPosition;
    private LinkedListData data;
    private Font font;
    private TextShader textShader;
    private BorderShader borderShader;
    private ArrowShader arrowShader;

    @Override
    public void setup() {
        this.cameraPosition = (CameraPosition) Component.getComponent("camera", "position");
        this.linkedListPosition = (Position) Component.getComponent("list", "position");
        this.data = (LinkedListData) Component.getComponent("list", "data");
        this.font = FontManager.getInstance().getFont();
        this.textShader = new TextShader(cameraPosition, linkedListPosition, "");
        this.borderShader = new BorderShader(cameraPosition, linkedListPosition, 1, 1);
        this.arrowShader = new ArrowShader(cameraPosition, linkedListPosition);
    }

    @Override
    public void render() {
        EntityPosition position = new EntityPosition(linkedListPosition);
        LinkedListData.Node current = data.getHead();

        while (current != null) {
            String stringValue = String.valueOf(current.getValue());
            float width = font.calculateWidth(stringValue);
            float height = font.calculateHeight(stringValue);

            textShader.setTextPosition(position);
            textShader.setText(stringValue);
            textShader.use();

            borderShader.setEntityPosition(position);
            borderShader.setWidth(width);
            borderShader.setHeight(height);
            borderShader.use();

            if (current.getNext() != null) {
                position.addX(width + 72.0f);

                arrowShader.setPosition(position);
                arrowShader.use();

                position.addX(136.0f);
            }

            current = current.getNext();
        }
    }
}
