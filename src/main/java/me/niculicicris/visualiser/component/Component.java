package me.niculicicris.visualiser.component;

import me.niculicicris.visualiser.entity.Entity;
import me.niculicicris.visualiser.scene.SceneManager;

import java.util.Optional;

public interface Component {

    static Component getComponent(String entityName, String componentName) {
        Optional<Entity> entity = SceneManager.getInstance().getScene().getEntity(entityName);

        if (entity.isEmpty()) {
            throw new IllegalStateException("There is no entity with name " + entityName + ".");
        }
        Optional<Component> component = entity.get().getComponent(componentName);

        if (component.isEmpty()) {
            throw new IllegalStateException("There is no component with name " + componentName + ".");
        }

        return component.get();
    }
}
