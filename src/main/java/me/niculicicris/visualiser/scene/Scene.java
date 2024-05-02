package me.niculicicris.visualiser.scene;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.Renderable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class Scene {
    private Map<String, Component> components = new HashMap<>();

    public Scene() {

    }

    public Optional<Component> getComponent(String name) {
        return Optional.ofNullable(components.get(name));
    }

    public void addComponent(String name, Component component) {
        if (components.containsKey(name)) {
            throw new IllegalArgumentException("Component with name " + name + " already exists.");
        }

        components.put(name, component);
    }

    public void forEachRenderableComponent(Consumer<Renderable> action) {
        for (Component component : components.values()) {
            if ((!(component instanceof Renderable renderable))) continue;
            action.accept(renderable);
        }
    }
}
