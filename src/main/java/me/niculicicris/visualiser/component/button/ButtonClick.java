package me.niculicicris.visualiser.component.button;

import me.niculicicris.visualiser.component.Clickable;
import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.position.CameraPosition;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.font.Font;
import me.niculicicris.visualiser.font.FontManager;
import me.niculicicris.visualiser.listener.MouseListener;
import me.niculicicris.visualiser.window.Window;
import org.joml.Vector4f;

public abstract class ButtonClick implements Clickable {
    private final String parentName;
    private final String label;
    private Position position;
    private CameraPosition cameraPosition;
    private Window window;
    private float width;
    private float height;

    protected ButtonClick(String parentName, String label) {
        this.parentName = parentName;
        this.label = label;
    }

    @Override
    public void setup() {
        Font font = FontManager.getInstance().getFont();

        this.position = (Position) Component.getComponent(parentName, "position");
        this.cameraPosition = (CameraPosition) Component.getComponent("camera", "position");
        this.window = Window.getInstance();
        this.width = font.calculateWidth(label);
        this.height = font.calculateHeight(label);
    }

    @Override
    public boolean isHovered() {
        Vector4f mousePosition = getMousePosition();

        float leftDownX = position.getX() - 40.0f;
        float leftDownY = position.getY() - 32.0f;

        float rightTopX = position.getX() + width + 40.0f;
        float rightTopY = position.getY() + height + 32.0f;

        return mousePosition.x >= leftDownX && mousePosition.x <= rightTopX &&
                mousePosition.y >= leftDownY && mousePosition.y <= rightTopY;
    }

        private Vector4f getMousePosition() {
        float currentX = MouseListener.getX();
        float currentY = MouseListener.getY();

        float ndcX = 2 * currentX / window.getWidth() - 1;
        float ndcY = 1 - 2 * currentY / window.getHeight();
        Vector4f normalizedCoordinates = new Vector4f(ndcX, ndcY, 0, 1.0f);

        return cameraPosition.getInvertProjection().transform(normalizedCoordinates);
    }

    @Override
    public boolean isClicked() {
        return MouseListener.isLeftButtonReleased() && isHovered();
    }
}
