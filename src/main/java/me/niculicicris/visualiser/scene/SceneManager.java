package me.niculicicris.visualiser.scene;

import me.niculicicris.visualiser.entity.Camera;
import me.niculicicris.visualiser.entity.Entity;
import me.niculicicris.visualiser.entity.LinkedList;

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
        addEntities();
    }

    private void addEntities() {
        scene.addEntity(new Camera("camera"));
        scene.addEntity(new LinkedList("list"));
    }

    public void setupScene() {
        scene.forEachEntity(Entity::setupEntity);
    }

    public void updateScene(float deltaTime) {
        scene.forEachEntity(entity -> entity.updateEntity(deltaTime));
    }

    public void renderScene() {
        scene.forEachEntity(Entity::renderEntity);
    }

    public Scene getScene() {
        return scene;
    }
}
