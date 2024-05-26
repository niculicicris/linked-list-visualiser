package me.niculicicris.visualiser.component.transform;

import org.joml.Matrix4f;

public class CameraPosition implements Position {
    private float x;
    private float y;
    private Matrix4f view;
    private Matrix4f projection;

    public CameraPosition(float x, float y) {
        this.x = x;
        this.y = y;

        updateView();
        updateProjection();
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
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public Matrix4f getView() {
        return view;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    private void updateView() {
        this.view = new Matrix4f().setTranslation(-this.x, -this.y, 0);
    }

    private void updateProjection() {
        this.projection = (new Matrix4f()).setOrtho(0, 1920, 0, 1080, 0, -100);
    }
}
