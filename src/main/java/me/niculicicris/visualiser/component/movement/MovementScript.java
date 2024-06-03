package me.niculicicris.visualiser.component.movement;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.Script;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.listener.KeyboardListener;

import static org.lwjgl.glfw.GLFW.*;

public class MovementScript implements Script {
    private static final float SPEED = 400;
    private final String parentName;
    private Position entityPosition;

    public MovementScript(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public void setup() {
        entityPosition = (Position) Component.getComponent(parentName, "position");
    }

    @Override
    public void update(float deltaTime) {
        float xInput = getXInput();
        float yInput = getYInput();

        entityPosition.addX(SPEED * xInput * deltaTime);
        entityPosition.addY(SPEED * yInput * deltaTime);
    }

    private float getXInput() {
        float input = 0;

        if (KeyboardListener.isKeyPressed(GLFW_KEY_LEFT)) {
            input -= 1;
        }

        if (KeyboardListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            input += 1;
        }

        return input;
    }

    private float getYInput() {
        float input = 0;

        if (KeyboardListener.isKeyPressed(GLFW_KEY_DOWN)) {
            input -= 1;
        }

        if (KeyboardListener.isKeyPressed(GLFW_KEY_UP)) {
            input += 1;
        }

        return input;
    }
}
