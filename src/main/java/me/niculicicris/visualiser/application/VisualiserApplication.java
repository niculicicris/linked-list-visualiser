package me.niculicicris.visualiser.application;

import me.niculicicris.visualiser.font.FontManager;
import me.niculicicris.visualiser.listener.KeyboardListener;
import me.niculicicris.visualiser.listener.MouseListener;
import me.niculicicris.visualiser.scene.SceneManager;
import me.niculicicris.visualiser.time.Time;
import me.niculicicris.visualiser.window.Window;

public class VisualiserApplication {
    private static final Window window = Window.getInstance();
    private static final FontManager fontManager = FontManager.getInstance();
    private static final SceneManager sceneManager = SceneManager.getInstance();

    private static float beginTime;
    private static float deltaTime;
    private static float endTime;

    public static void run() {
        initialize();
        startLoop();
        cleanup();
    }

    private static void initialize() {
        initializeWindow();
        initializeFontManager();
        initializeSceneManager();
        initializeTime();
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

    private static void initializeTime() {
        beginTime = Time.getTime();
        deltaTime = -1;
    }

    private static void startLoop() {
        sceneManager.setupScene();
        while (!window.shouldClose()) {
            if (deltaTime >= 0) {
                update(deltaTime);
                render();
                flush();
            }

            registerTime();
        }
    }

    private static void update(float deltaTime) {
        window.pollWindowEvents();
        sceneManager.updateScene(deltaTime);
    }

    private static void render() {
        window.clearWindow();
        sceneManager.renderScene();
        window.renderWindow();
    }

    private static void flush() {
        MouseListener.flushReleasedButtons();
        KeyboardListener.flushReleasedKeys();
    }

    private static void registerTime() {
        endTime = Time.getTime();
        deltaTime = endTime - beginTime;
        beginTime = endTime;
    }

    private static void cleanup() {
        window.cleanup();
    }
}
