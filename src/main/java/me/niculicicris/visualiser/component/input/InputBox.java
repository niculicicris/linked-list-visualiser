package me.niculicicris.visualiser.component.input;

import me.niculicicris.visualiser.component.Clickable;
import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.position.CameraPosition;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.font.Font;
import me.niculicicris.visualiser.font.FontManager;
import me.niculicicris.visualiser.listener.MouseListener;
import me.niculicicris.visualiser.window.Window;
import org.joml.Vector4f;

public class InputBox implements Clickable {
    private final String parentName;
    private Position position;
    private CameraPosition cameraPosition;
    private Window window;
    private InputData inputData;
    private float width;
    private float height;
    private boolean focus;

    public InputBox(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public void setup() {
        Font font = FontManager.getInstance().getFont();

        this.position = (Position) Component.getComponent(parentName, "position");
        this.cameraPosition = (CameraPosition) Component.getComponent("camera", "position");
        this.window = Window.getInstance();
        this.inputData = (InputData) Component.getComponent(parentName, "data");
        this.width = font.calculateWidth("1234567");
        this.height = font.calculateHeight("1234567");
    }

    @Override
    public void update(float deltaTime) {
        updateFocus();
        if (focus) {
            inputData.processKeyboardInput();
        }
    }

    private void updateFocus() {
        if (isClicked()) {
            focus = true;
        } else if (MouseListener.isLeftButtonReleased()) {
            focus = false;
        }
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
