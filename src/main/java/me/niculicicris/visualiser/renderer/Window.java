package me.niculicicris.visualiser.renderer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long windowId;

    private static Window instance;

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
    }

    private void initializeGlfw() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }
    }

    private void initializeWindowHints() {
        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
    }

    private void initializeGlfwWindow() {
        createGlfwWindow();
        glfwMakeContextCurrent(windowId);
        glfwSwapInterval(1);
        glfwShowWindow(windowId);
        GL.createCapabilities();
    }

    private void createGlfwWindow() {
        windowId = glfwCreateWindow(1920, 1080, "Visualiser", NULL, NULL);

        if (windowId == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowId);
    }

    public void loop(Consumer<Void> consumer) {
        pollEvents();
        consumer.accept(null);
        render();
    }

    private void pollEvents() {
        glfwPollEvents();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        glfwSwapBuffers(windowId);
    }

    public void destroy() {
        glfwDestroyWindow(windowId);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
