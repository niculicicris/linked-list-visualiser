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
        window = new Window();
        window.initialize();
    }

    private void initializeSceneManager() {
        Scene scene = new Scene();
        sceneManager = new SceneManager(scene);
    }

    private void loop() {
        while (!window.shouldClose()) {
            window.pollWindowEvents(sceneManager::handleSceneEvents);
            window.renderWindowContents(sceneManager::renderScene);
        }
    }

    private void cleanup() {
        window.cleanup();
    }
}
