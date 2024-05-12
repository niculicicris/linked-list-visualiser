package me.niculicicris.visualiser.entity;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.Renderer;
import me.niculicicris.visualiser.component.Script;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Entity {
    private final Map<String, Component> components = new HashMap<>();

    public void addComponent(String name, Component component) {
        components.put(name, component);
    }

    public Optional<Component> getComponent(String name) {
        return Optional.ofNullable(components.get(name));
    }

    public void updateEntity() {
        components.values().forEach(component -> {
            if (!(component instanceof Script script)) return;
            script.update();
        });
    }

    public void renderEntity() {
        components.values().forEach(component -> {
            if (!(component instanceof Renderer renderer)) return;
            renderer.render();
        });
    }
}
