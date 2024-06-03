package me.niculicicris.visualiser.component.position;

import org.joml.Matrix4f;

public class CameraPosition extends EntityPosition {
    private Matrix4f projection;
    private Matrix4f view;
    private final Matrix4f identity = new Matrix4f().identity();

    public CameraPosition(final float x, final float y) {
        super(x, y);
        updateProjection();
        updateView();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        updateView();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        updateView();
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        return view;
    }

    public Matrix4f getIdentity() {
        return identity;
    }

    private void updateProjection() {
        this.projection = (new Matrix4f()).setOrtho(0, 1920, 0, 1080, 0, -100);
    }

    private void updateView() {
        this.view = new Matrix4f().setTranslation(-getX(), -getY(), 0);
    }
}
