package me.niculicicris.visualiser.listener;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
    private static MouseListener instance;
    private double x;
    private double y;
    private final boolean[] buttonPressed;

    private MouseListener() {
        this.x = 0;
        this.y = 0;
        this.buttonPressed = new boolean[3];
    }

    public static MouseListener getInstance() {
        if (instance == null) {
            instance = new MouseListener();
        }

        return instance;
    }

    public static void mousePositionCallback(long window, double x, double y) {
        getInstance().x = x;
        getInstance().y = y;
    }

    public static void mouseButtonClickCallback(long window, int button, int action, int mods) {
        if (button > 2) return;
        getInstance().buttonPressed[button] = action == GLFW_PRESS;
    }

    public static double getX() {
        return getInstance().x;
    }

    public static double getY() {
        return getInstance().y;
    }

    public static boolean isLeftButtonPressed() {
        return getInstance().buttonPressed[GLFW_MOUSE_BUTTON_LEFT];
    }

    public static boolean isRightButtonPressed() {
        return getInstance().buttonPressed[GLFW_MOUSE_BUTTON_RIGHT];
    }
}
