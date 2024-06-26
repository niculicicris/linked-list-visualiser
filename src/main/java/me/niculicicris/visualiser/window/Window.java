package me.niculicicris.visualiser.window;

import me.niculicicris.visualiser.listener.KeyboardListener;
import me.niculicicris.visualiser.listener.MouseListener;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private static Window instance;
    private long windowId;
    private int width;
    private  int height;

    private Window() {}

    public static Window getInstance() {
        if (instance == null) {
            instance = new Window();
        }

        return instance;
    }

    public void initialize() {
        initializeGlfw();
        initializeWindowHints();
        initializeGlfwWindow();
        enableBlending();
        saveWindowSize();
    }

    private void initializeGlfw() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }
    }

    private void initializeWindowHints() {
        glfwDefaultWindowHints();
        ;
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
    }

    private void initializeGlfwWindow() {
        createGlfwWindow();
        glfwMakeContextCurrent(windowId);
        initializeCallbacks();
        glfwSwapInterval(1);
        glfwShowWindow(windowId);
        createCapabilities();
    }

    private void createGlfwWindow() {
        windowId = glfwCreateWindow(1920, 1080, "Linked List Visualiser", NULL, NULL);

        if (windowId == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }
    }

    private void initializeCallbacks() {
        GLFWCursorPosCallback positionCallback = glfwSetCursorPosCallback(windowId, MouseListener::mousePositionCallback);
        GLFWMouseButtonCallback buttonClickCallback = glfwSetMouseButtonCallback(windowId, MouseListener::mouseButtonClickCallback);
        GLFWKeyCallback keyPressCallback = glfwSetKeyCallback(windowId, KeyboardListener::keyPressedCallback);

        if (positionCallback != null) {
            positionCallback.close();
        }

        if (buttonClickCallback != null) {
            buttonClickCallback.close();
        }

        if (keyPressCallback != null) {
            keyPressCallback.close();
        }
    }

    private void enableBlending() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void saveWindowSize() {
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);

        glfwGetWindowSize(windowId, widthBuffer, heightBuffer);

        this.width = widthBuffer.get(0);
        this.height = heightBuffer.get(0);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowId);
    }

    public void pollWindowEvents() {
        glfwPollEvents();
    }

    public void clearWindow() {
        glClearColor(0.09f, 0.09f, 0.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void renderWindow() {
        glfwSwapBuffers(windowId);
    }

    public void cleanup() {
        glfwFreeCallbacks(windowId);
        glfwDestroyWindow(windowId);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
