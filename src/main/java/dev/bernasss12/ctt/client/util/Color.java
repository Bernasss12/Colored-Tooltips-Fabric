package dev.bernasss12.ctt.client.util;

import net.minecraft.util.math.MathHelper;

public class Color {

    public static final Color WHITE = new Color(0xffffffff);

    private final int color;

    public Color(int color) {
        this.color = color;
    }

    public Color(int a, int r, int g, int b) {
        this(a << 24 | r << 16 | g << 8 | b);
    }

    public static Color fromLiteralString(String colorString) {
        return new Color(Integer.parseInt(colorString));
    }

    public static Color fromHexString(String hexString) {
        hexString = hexString.replace("#", "");
        if (hexString.length() > 8) throw new NumberFormatException("");
        return new Color((int) Long.parseLong(hexString, 16));
    }

    public Color darken(float darkeningFactor) {
        float clampedFactor = MathHelper.clamp(darkeningFactor, 0.1f, 1.0f);
        return new Color(alpha(), (int) (red() * clampedFactor), (int) (green() * clampedFactor), (int) (blue() * clampedFactor));
    }

    public Color getOpaque() {
        return new Color(255, red(), green(), blue());
    }

    public int alpha() {
        return (color >> 24 & 255);
    }

    public int red() {
        return (color >> 16 & 255);
    }

    public int green() {
        return (color >> 8 & 255);
    }

    public int blue() {
        return (color & 255);
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red() + "; " +
                "green=" + green() + "; " +
                "blue=" + blue() + "; " +
                "alpha=" + alpha() +
                '}';
    }

    public String toLiteralString() {
        return color + "";
    }

    public Color copy() {
        return new Color(this.color);
    }
}
