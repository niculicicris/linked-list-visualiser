package me.niculicicris.visualiser.entity;

import me.niculicicris.visualiser.component.position.CameraPosition;

public class Camera extends Entity {

    public Camera(String name) {
        super(name);

        addComponent("position", new CameraPosition(0, 0));
    }
}
