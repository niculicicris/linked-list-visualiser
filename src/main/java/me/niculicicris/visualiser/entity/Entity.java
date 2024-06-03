package me.niculicicris.visualiser.entity;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.renderer.Renderer;
import me.niculicicris.visualiser.component.Script;
import me.niculicicris.visualiser.component.Setup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Entity {
    private final String name;
    private final Map<String, Component> components = new HashMap<>();

    protected Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addComponent(String name, Component component) {
        components.put(name, component);
    }

    public Optional<Component> getComponent(String name) {
        return Optional.ofNullable(components.get(name));
    }

    public void setupEntity() {
        components.values().forEach(component -> {
            if (!(component instanceof Setup setup)) return;
            setup.setup();
        });
    }

    public void updateEntity(float deltaTime) {
        components.values().forEach(component -> {
            if (!(component instanceof Script script)) return;
            script.update(deltaTime);
        });
    }

    public void renderEntity() {
        components.values().forEach(component -> {
            if (!(component instanceof Renderer renderer)) return;
            renderer.render();
        });
    }
}
