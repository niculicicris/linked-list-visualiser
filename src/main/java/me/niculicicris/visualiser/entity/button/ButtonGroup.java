package me.niculicicris.visualiser.entity.button;

import me.niculicicris.visualiser.component.button.ButtonContainer;
import me.niculicicris.visualiser.component.position.EntityPosition;
import me.niculicicris.visualiser.entity.Entity;

import java.util.List;

public class ButtonGroup extends Entity {

    public ButtonGroup(String name, List<Button> buttons) {
        super(name);
        addComponent("position", new EntityPosition(0, 952));
        addComponent("container", new ButtonContainer(name, buttons));
    }
}
