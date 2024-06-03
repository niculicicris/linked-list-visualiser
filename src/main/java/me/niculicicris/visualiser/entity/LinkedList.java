package me.niculicicris.visualiser.entity;

import me.niculicicris.visualiser.component.LinkedListData;
import me.niculicicris.visualiser.component.position.EntityPosition;
import me.niculicicris.visualiser.component.renderer.LinkedListRenderer;
import me.niculicicris.visualiser.font.Font;
import me.niculicicris.visualiser.font.FontManager;

public class LinkedList extends Entity {

    public LinkedList(String name) {
        super(name);

        addPositionComponent();
        addDataComponent();
        addRendererComponent();
    }

    private void addPositionComponent() {
        Font font = FontManager.getInstance().getFont();

        float x = 96;
        float y = 540 - (float) font.getCharacterInfo('a').getHeight() / 2;

        addComponent("position", new EntityPosition(x, y));
    }

    private void addDataComponent() {
        LinkedListData data =  new LinkedListData(0);
        addComponent("data", data);
    }

    private void addRendererComponent() {
        addComponent("renderer", new LinkedListRenderer());
    }
}
