package me.niculicicris.visualiser.component.button;

import me.niculicicris.visualiser.component.Component;
import me.niculicicris.visualiser.component.Script;
import me.niculicicris.visualiser.component.position.EntityPosition;
import me.niculicicris.visualiser.component.position.Position;
import me.niculicicris.visualiser.entity.button.Button;
import me.niculicicris.visualiser.font.Font;
import me.niculicicris.visualiser.font.FontManager;

import java.util.List;

public class ButtonContainer implements Script {
    private final String parentName;
    private final List<Button> buttons;
    private float gap;
    private Position position;
    private Font font;

    public ButtonContainer(String parentName, List<Button> buttons) {
        this.parentName = parentName;
        this.buttons = buttons;
    }

    @Override
    public void setup() {
        this.position = (Position) Component.getComponent(parentName, "position");
        this.font = FontManager.getInstance().getFont();

        calculateGap();
    }

    private void calculateGap() {
        float totalWidth = 0;

        for (Button button : buttons) {
            totalWidth += font.calculateWidth(button.getLabel()) + 80.0f;
        }

        gap = (1920 - totalWidth) / (buttons.size() + 1);
    }

    @Override
    public void update(float deltaTime) {
        Position currentPosition = new EntityPosition(position);

        currentPosition.setX(gap + 40.0f);
        for (Button button : buttons) {
            Position buttonPosition = button.getPosition();
            float width = font.calculateWidth(button.getLabel());

            buttonPosition.setX(currentPosition.getX());
            buttonPosition.setY(currentPosition.getY());

            currentPosition.addX(gap + width + 80.0f);
        }
    }
}
