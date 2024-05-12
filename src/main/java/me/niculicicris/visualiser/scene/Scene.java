package me.niculicicris.visualiser.scene;

import me.niculicicris.visualiser.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class Scene {
    private final Map<String, Entity> entities = new HashMap<>();

    public void addEntity(String name, Entity entity) {
        entities.put(name, entity);
    }

    public Optional<Entity> getEntity(String name) {
        return Optional.of(entities.get(name));
    }

    public void forEachEntity(Consumer<Entity> consumer) {
        entities.values().forEach(consumer);
    }
}
