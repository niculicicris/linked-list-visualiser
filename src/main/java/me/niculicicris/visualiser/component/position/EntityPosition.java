package me.niculicicris.visualiser.component.position;

public class EntityPosition implements Position {
    private float x;
    private float y;

    public EntityPosition(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public EntityPosition(Position original) {
        this.x = original.getX();
        this.y = original.getY();
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void addX(float x) {
        this.x += x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void addY(float y) {
        this.y += y;
    }
}
