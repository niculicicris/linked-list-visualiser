package me.niculicicris.visualiser.listener;

import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardListener {
    private static KeyboardListener instance;
    private boolean[] keyPressed = new boolean[350];
    private boolean[] keyReleased = new boolean[350];

    private KeyboardListener() {
    }

    public static KeyboardListener getInstance() {
        if (instance == null) {
            instance = new KeyboardListener();
        }
        return instance;
    }

    public static void keyPressedCallback(long window, int key, int scancode, int action, int mods) {
        getInstance().keyPressed[key] = action == GLFW_PRESS || action == GLFW_REPEAT;
        getInstance().keyReleased[key] = action == GLFW_RELEASE;
    }

    public static boolean isKeyPressed(int key) {
        return getInstance().keyPressed[key];
    }

    public static boolean isKeyReleased(int key) {
        return getInstance().keyReleased[key];
    }

    public static void flushReleasedKeys() {
        getInstance().keyReleased = new boolean[350];
    }
}