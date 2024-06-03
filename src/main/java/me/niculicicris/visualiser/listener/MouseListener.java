package me.niculicicris.visualiser.listener;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
    private static MouseListener instance;
    private float x;
    private float y;
    private boolean[] buttonReleased;

    private MouseListener() {
        this.x = 0;
        this.y = 0;
        this.buttonReleased = new boolean[3];
    }

    public static MouseListener getInstance() {
        if (instance == null) {
            instance = new MouseListener();
        }

        return instance;
    }

    public static void mousePositionCallback(long window, double x, double y) {
        getInstance().x = (float) x;
        getInstance().y = (float) y;
    }

    public static void mouseButtonClickCallback(long window, int button, int action, int mods) {
        if (button > 2) return;
        getInstance().buttonReleased[button] = action == GLFW_RELEASE;
    }

    public static float getX() {
        return getInstance().x;
    }

    public static float getY() {
        return getInstance().y;
    }

    public static boolean isLeftButtonReleased() {
        return getInstance().buttonReleased[GLFW_MOUSE_BUTTON_LEFT];
    }

    public static boolean isRightButtonReleased() {
        return getInstance().buttonReleased[GLFW_MOUSE_BUTTON_RIGHT];
    }

    public static void flushReleasedButtons() {
        getInstance().buttonReleased = new boolean[3];
    }
}
