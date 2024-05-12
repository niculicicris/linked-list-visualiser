package me.niculicicris.visualiser.scene;

import me.niculicicris.visualiser.entity.Entity;

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

    public void initialize() {
        scene = new Scene();
    }

    public void updateScene() {
        scene.forEachEntity(Entity::updateEntity);
    }

    public void renderScene() {
        scene.forEachEntity(Entity::renderEntity);
    }
}
