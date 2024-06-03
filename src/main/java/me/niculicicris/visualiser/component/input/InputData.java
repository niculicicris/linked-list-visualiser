package me.niculicicris.visualiser.component.input;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.listener.KeyboardListener;

import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;

public class InputData implements Component {
    private String stringValue = "";

    public void processKeyboardInput() {
        Queue<Integer> keysQueue = KeyboardListener.getKeysQueue();

        while (!keysQueue.isEmpty()) {
            int key = keysQueue.remove();

            if (key == GLFW_KEY_BACKSPACE && stringValue.isEmpty()) continue;
            if (key == GLFW_KEY_MINUS && !stringValue.isEmpty()) continue;

            if (key == GLFW_KEY_BACKSPACE) {
                stringValue = stringValue.substring(0, stringValue.length() - 1);
                continue;
            }

            if (stringValue.startsWith("-") && stringValue.length() == 8) continue;
            if (!stringValue.startsWith("-") && stringValue.length() == 7) continue;

            if (key == GLFW_KEY_MINUS) {
                stringValue = "-";
                continue;
            }

            if (stringValue.startsWith("0")) continue;
            if (key < GLFW_KEY_0 || key > GLFW_KEY_9) continue;
            stringValue += (key - GLFW_KEY_0);
        }
    }

    public String getStringValue() {
        return stringValue;
    }

    public void clearValue() {
        stringValue = "";
    }
}
