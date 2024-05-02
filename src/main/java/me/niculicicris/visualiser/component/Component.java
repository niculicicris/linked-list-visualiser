package me.niculicicris.visualiser.component;

import me.niculicicris.visualiser.manger.SceneManager;

import java.util.Optional;

public interface Component {

    static Optional<Component> getComponent(String name) {
        return SceneManager.getInstance().getScene().getComponent(name);
    }
}
