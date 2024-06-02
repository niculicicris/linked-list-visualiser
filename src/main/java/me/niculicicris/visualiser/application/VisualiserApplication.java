package me.niculicicris.visualiser.application;

import me.niculicicris.visualiser.font.FontManager;
import me.niculicicris.visualiser.scene.SceneManager;
import me.niculicicris.visualiser.window.Window;

public class VisualiserApplication {
    private static final Window window = Window.getInstance();
    private static final FontManager fontManager = FontManager.getInstance();
    private static final SceneManager sceneManager = SceneManager.getInstance();

    public static void run() {
        initialize();
        startLoop();
        cleanup();
    }

    private static void initialize() {
        initializeWindow();
        initializeFontManager();
        initializeSceneManager();
    }

    private static void initializeWindow() {
        window.initialize();
    }

    private static void initializeFontManager() {
        fontManager.initialize();
    }

    private static void initializeSceneManager() {
        sceneManager.initialize();
    }

    private static void startLoop() {
        sceneManager.setupScene();
        while (!window.shouldClose()) {
            update();
            render();
        }
    }

    private static void update() {
        window.pollWindowEvents();
        sceneManager.updateScene();
    }

    private static void render() {
        window.clearWindow();
        sceneManager.renderScene();
        window.renderWindow();
    }

    private static void cleanup() {
        window.cleanup();
    }
}
