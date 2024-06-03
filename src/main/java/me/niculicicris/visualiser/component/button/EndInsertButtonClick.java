package me.niculicicris.visualiser.component.button;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.input.InputData;
import me.niculicicris.visualiser.component.list.LinkedListData;

public class EndInsertButtonClick extends ButtonClick {
    private LinkedListData data;
    private InputData inputData;

    public EndInsertButtonClick(String parentName, String label) {
        super(parentName, label);
    }

    @Override
    public void setup() {
        super.setup();

        this.data = (LinkedListData) Component.getComponent("list", "data");
        this.inputData = (InputData) Component.getComponent("input", "data");
    }

    @Override
    public void update(float deltaTime) {
        if (!isClicked()) return;
        if (inputData.getStringValue().isEmpty()) return;
        int value = Integer.parseInt(inputData.getStringValue());

        data.insertAtEnd(value);
        inputData.clearValue();
    }
}
