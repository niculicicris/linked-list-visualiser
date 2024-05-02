package me.niculicicris.visualiser.renderer;

import me.niculicicris.visualiser.component.Renderable;
import me.niculicicris.visualiser.scene.Scene;

public class SceneManager {
    private static SceneManager instance;
    private Scene scene;

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }

        return instance;
    }

    public void handleSceneEvents() {

    }

    public void renderScene() {
        scene.forEachRenderableComponent(Renderable::render);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
