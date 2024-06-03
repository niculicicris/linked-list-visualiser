package me.niculicicris.visualiser.entity;

import me.niculicicris.visualiser.component.input.InputBox;
import me.niculicicris.visualiser.component.input.InputData;
import me.niculicicris.visualiser.component.position.EntityPosition;
import me.niculicicris.visualiser.component.renderer.InputRenderer;

public class Input extends Entity {

    public Input(String name) {
        super(name);

        addComponent("position", new EntityPosition(0, 128));
        addComponent("data", new InputData());
        addComponent("input_box", new InputBox(name));
        addComponent("renderer", new InputRenderer(name));
    }
}
