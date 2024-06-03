package me.niculicicris.visualiser.scene;

import me.niculicicris.visualiser.component.button.BeginDeleteButtonClick;
import me.niculicicris.visualiser.component.button.BeginInsertButtonClick;
import me.niculicicris.visualiser.component.button.EndDeleteButtonClick;
import me.niculicicris.visualiser.component.button.EndInsertButtonClick;
import me.niculicicris.visualiser.entity.Camera;
import me.niculicicris.visualiser.entity.Entity;
import me.niculicicris.visualiser.entity.LinkedList;
import me.niculicicris.visualiser.entity.input.Button;
import me.niculicicris.visualiser.entity.input.ButtonGroup;

import java.util.ArrayList;
import java.util.List;

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
        addCamera();
        addList();
        addButtons();
    }

    private void addCamera() {
        scene.addEntity(new Camera("camera"));
    }

    private void addList() {
        scene.addEntity(new LinkedList("list"));
    }

    private void addButtons() {
        List<Button> buttons = new ArrayList<>();

        buttons.add(addBeginInsertButton());
        buttons.add(addEndInsertButton());
        buttons.add(addBeginDeleteButton());
        buttons.add(addEndDeleteButton());

        scene.addEntity(new ButtonGroup("buttons", buttons));
    }

    private Button addBeginInsertButton() {
        String name = "begin_insert";
        String label = "Begin Insert";

        BeginInsertButtonClick click = new BeginInsertButtonClick(name, label);
        Button button = new Button(name, label, click);

        scene.addEntity(button);

        return button;
    }

    private Button addEndInsertButton() {
        String name = "end_insert";
        String label = "End Insert";

        EndInsertButtonClick click = new EndInsertButtonClick(name, label);
        Button button = new Button(name, label, click);

        scene.addEntity(button);

        return button;
    }

    private Button addBeginDeleteButton() {
        String name = "begin_delete";
        String label = "Begin Delete";

        BeginDeleteButtonClick click = new BeginDeleteButtonClick(name, label);
        Button button = new Button(name, label, click);

        scene.addEntity(button);

        return button;
    }

    private Button addEndDeleteButton() {
        String name = "end_delete";
        String label = "End Delete";

        EndDeleteButtonClick click = new EndDeleteButtonClick(name, label);
        Button button = new Button(name, label, click);

        scene.addEntity(button);

        return button;
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
