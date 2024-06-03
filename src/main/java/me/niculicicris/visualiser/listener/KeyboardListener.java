package me.niculicicris.visualiser.listener;

import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardListener {
    private static KeyboardListener instance;
    private final boolean[] keyPressed = new boolean[350];
    private final Queue<Integer> releasedKeys = new LinkedList<>();

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

        if (action == GLFW_PRESS) {
            getInstance().releasedKeys.add(key);
        }
    }

    public static boolean isKeyPressed(int key) {
        return getInstance().keyPressed[key];
    }

    public static Queue<Integer> getKeysQueue() {
        return new LinkedList<>(getInstance().releasedKeys);
    }

    public static void flushKeysQueue() {
        getInstance().releasedKeys.clear();
    }
}