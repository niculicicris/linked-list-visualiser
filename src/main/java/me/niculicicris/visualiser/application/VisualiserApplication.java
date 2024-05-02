package me.niculicicris.visualiser.application;

import me.niculicicris.visualiser.component.LinkedListComponent;
import me.niculicicris.visualiser.manger.SceneManager;
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
        Scene scene = new Scene();

        LinkedListComponent list = new LinkedListComponent();
        list.insertAtBeginning(1);
        list.insertAtEnd(11);
        list.insertAtEnd(42);
        list.insertAtEnd(234);

        scene.addComponent("list", list);

        sceneManager = SceneManager.getInstance();
        sceneManager.setScene(scene);
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
