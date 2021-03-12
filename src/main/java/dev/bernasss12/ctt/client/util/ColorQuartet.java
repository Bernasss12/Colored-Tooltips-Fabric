package dev.bernasss12.ctt.client.util;

public class ColorQuartet {

    public Color topRight, topLeft, bottomLeft, bottomRight;

    public ColorQuartet(Color topRight, Color topLeft, Color bottomLeft, Color bottomRight) {
        this.topRight = new Color(topRight.getColor());
        this.topLeft = new Color(topLeft.getColor());
        this.bottomLeft = new Color(bottomLeft.getColor());
        this.bottomRight = new Color(bottomRight.getColor());
    }

    public ColorQuartet(Color top, Color bottom) {
        this(top, top, bottom, bottom);
    }

    public ColorQuartet(Color color) {
        this(color, color);
    }

    public ColorQuartet darken(float darkeningFactor) {
        return new ColorQuartet(
                topRight.darken(darkeningFactor),
                topLeft.darken(darkeningFactor),
                bottomLeft.darken(darkeningFactor),
                bottomRight.darken(darkeningFactor)
        );
    }

    public ColorQuartet darkenBottom(float darkeningFactor) {
        return new ColorQuartet(
                topRight,
                topLeft,
                bottomLeft.darken(darkeningFactor),
                bottomRight.darken(darkeningFactor)
        );
    }
}
