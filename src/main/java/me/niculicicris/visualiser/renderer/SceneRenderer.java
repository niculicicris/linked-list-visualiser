package me.niculicicris.visualiser.renderer;

import me.niculicicris.visualiser.scene.Scene;

public class SceneRenderer {
    private Scene currentScene;

    private static SceneRenderer instance;

    private SceneRenderer() {}

    public static SceneRenderer getInstance() {
        if (instance == null) {
            instance = new SceneRenderer();
        }

        return instance;
    }

    public void switchScene(Scene newScene) {
        currentScene = newScene;
    }

    public void run() {
        Window window = Window.getInstance();

        while (!window.shouldClose()) {
            window.loop(_ -> renderScene());
        }
    }

    private void renderScene() {
        System.out.println("Hello, world!");
    }
}
