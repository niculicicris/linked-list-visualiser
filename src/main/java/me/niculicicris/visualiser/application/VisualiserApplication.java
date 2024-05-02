package me.niculicicris.visualiser.application;

import me.niculicicris.visualiser.renderer.SceneManager;
import me.niculicicris.visualiser.renderer.Window;
import me.niculicicris.visualiser.scene.Scene;

public class VisualiserApplication {
    private SceneManager sceneManager;
    private Window window;

    public void run() {
        initialize();
        loop();
        cleanup();
    }

    private void initialize() {
        initializeWindow();
        initializeSceneManager();
    }

    private void initializeWindow() {
        window = Window.getInstance();
        window.initialize();
    }

    private void initializeSceneManager() {
        sceneManager = SceneManager.getInstance();
        sceneManager.setScene(new Scene());
    }

    private void loop() {
        while (!window.shouldClose()) {
            window.pollWindowEvents(sceneManager::handleSceneEvents);
            window.renderWindow(sceneManager::renderScene);
        }
    }

    private void cleanup() {
        window.cleanup();
    }
}
