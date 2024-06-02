package me.niculicicris.visualiser.font;

import org.joml.Vector2f;

public class CharacterInfo {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int xOffset;
    private final int yOffset;
    private final int xAdvance;
    private final Vector2f[] normalizedPoints;

    public CharacterInfo(int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xAdvance = xAdvance;
        this.normalizedPoints = new Vector2f[4];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public int getXAdvance() {
        return xAdvance;
    }

    public Vector2f getLeftDownPoint() {
        return normalizedPoints[0];
    }

    public Vector2f getRightDownPoint() {
        return normalizedPoints[1];
    }

    public Vector2f getRightTopPoint() {
        return normalizedPoints[2];
    }

    public Vector2f getLeftTopPoint() {
        return normalizedPoints[3];
    }

    public void normalize(int textureWidth, int textureHeight) {
        normalizedPoints[3] = new Vector2f();
        normalizedPoints[3].x = (float) x / textureWidth;
        normalizedPoints[3].y = (float) y / textureHeight;

        normalizedPoints[1] = new Vector2f();
        normalizedPoints[1].x = (float) (x + width) / textureWidth;
        normalizedPoints[1].y = (float) (y + height) / textureHeight;

        normalizedPoints[2] = new Vector2f();
        normalizedPoints[2].x = (float) (x + width) / textureWidth;
        normalizedPoints[2].y = (float) y / textureHeight;

        normalizedPoints[0] = new Vector2f();
        normalizedPoints[0].x = (float) x / textureWidth;
        normalizedPoints[0].y = (float) (y + height) / textureHeight;
    }
}
