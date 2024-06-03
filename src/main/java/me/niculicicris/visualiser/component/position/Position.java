package me.niculicicris.visualiser.component.position;

import me.niculicicris.visualiser.component.Component;

public interface Position extends Component {
    float getX();
    void setX(float x);
    void addX(float x);
    float getY();
    void setY(float y);
    void addY(float y);
}
