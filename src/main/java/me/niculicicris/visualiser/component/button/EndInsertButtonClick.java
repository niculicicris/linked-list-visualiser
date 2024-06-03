package me.niculicicris.visualiser.component.button;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.list.LinkedListData;

public class EndInsertButtonClick extends ButtonClick {
    private LinkedListData data;

    public EndInsertButtonClick(String parentName, String label) {
        super(parentName, label);
    }

    @Override
    public void setup() {
        super.setup();
        this.data = (LinkedListData) Component.getComponent("list", "data");
    }

    @Override
    public void update(float deltaTime) {
        if (isClicked()) {
            data.insertAtEnd(10);
        }
    }
}
