package me.niculicicris.visualiser.renderer;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long windowId;

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
        createCapabilities();
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

    public void pollWindowEvents(Runnable handleSceneEvents) {
        glfwPollEvents();
        handleSceneEvents.run();
    }

    public void renderWindowContents(Runnable renderScene) {
        renderScene.run();
        glClear(GL_COLOR_BUFFER_BIT);
        glfwSwapBuffers(windowId);
    }

    public void cleanup() {
        glfwDestroyWindow(windowId);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
