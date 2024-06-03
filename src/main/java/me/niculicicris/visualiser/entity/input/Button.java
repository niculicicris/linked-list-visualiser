package me.niculicicris.visualiser.entity.input;

import me.niculicicris.visualiser.component.Clickable;
import me.niculicicris.visualiser.component.position.EntityPosition;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.component.renderer.ButtonRenderer;
import me.niculicicris.visualiser.entity.Entity;

public class Button extends Entity {
    private final String label;

    public Button(String name, String label, Clickable clickable) {
        super(name);
        this.label = label;

        addComponent("position", new EntityPosition(0, 0));
        addComponent("click", clickable);
        addComponent("renderer", new ButtonRenderer(name, label));
    }

    public String getLabel() {
        return label;
    }

    public Position getPosition() {
        return (Position) getComponent("position").get();
    }
}
