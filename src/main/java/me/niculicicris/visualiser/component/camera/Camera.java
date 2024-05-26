package me.niculicicris.visualiser.component.camera;

import me.niculicicris.visualiser.component.transform.CameraPosition;
import me.niculicicris.visualiser.entity.Entity;

public class Camera extends Entity {

    public Camera() {
        addComponent("position", new CameraPosition(0, 0));
    }
}
