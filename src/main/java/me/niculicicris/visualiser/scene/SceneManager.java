package me.niculicicris.visualiser.scene;

import me.niculicicris.visualiser.component.button.*;
import me.niculicicris.visualiser.entity.Camera;
import me.niculicicris.visualiser.entity.Entity;
import me.niculicicris.visualiser.entity.Input;
import me.niculicicris.visualiser.entity.LinkedList;
import me.niculicicris.visualiser.entity.button.Button;
import me.niculicicris.visualiser.entity.button.ButtonGroup;

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
        addInput();
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
        buttons.add(addBeginRemoveButton());
        buttons.add(addEndRemoveButton());
        buttons.add(addDeleteButton());

        scene.addEntity(new ButtonGroup("buttons", buttons));
    }

    private Button addBeginInsertButton() {
        String name = "begin_insert";
        String label = "Beginning Insert";

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

    private Button addBeginRemoveButton() {
        String name = "begin_delete";
        String label = "Beginning Remove";

        BeginRemoveButtonClick click = new BeginRemoveButtonClick(name, label);
        Button button = new Button(name, label, click);

        scene.addEntity(button);

        return button;
    }

    private Button addEndRemoveButton() {
        String name = "end_delete";
        String label = "End Remove";

        EndRemoveButtonClick click = new EndRemoveButtonClick(name, label);
        Button button = new Button(name, label, click);

        scene.addEntity(button);

        return button;
    }

    private Button addDeleteButton() {
        String name = "delete";
        String label = "Remove";

        RemoveButtonClick click = new RemoveButtonClick(name, label);
        Button button = new Button(name, label, click);

        scene.addEntity(button);

        return button;
    }

    private void addInput() {
        scene.addEntity(new Input("input"));
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
