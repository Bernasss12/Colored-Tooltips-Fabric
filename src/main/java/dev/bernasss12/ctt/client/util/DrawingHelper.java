package dev.bernasss12.ctt.client.util;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.util.math.Matrix4f;

public class DrawingHelper {
    public static void fillVerticalGradient(Matrix4f matrix, BufferBuilder bufferBuilder, int xStart, int yStart, int xEnd, int yEnd, int z, Color colorTop, Color colorBottom) {
        fillGradient(matrix, bufferBuilder, xStart, yStart, xEnd, yEnd, z, colorTop, colorTop, colorBottom, colorBottom);
    }

    public static void fillHorizontalGradient(Matrix4f matrix, BufferBuilder bufferBuilder, int xStart, int yStart, int xEnd, int yEnd, int z, Color colorLeft, Color colorRight) {
        fillGradient(matrix, bufferBuilder, xStart, yStart, xEnd, yEnd, z, colorRight, colorLeft, colorLeft, colorRight);
    }

    public static void fillGradient(Matrix4f matrix, BufferBuilder bufferBuilder, int xStart, int yStart, int xEnd, int yEnd, int z, Color colorTopRight, Color colorTopLeft, Color colorBottomLeft, Color colorBottomRight) {
        bufferBuilder.vertex(matrix, (float) xEnd, (float) yStart, (float) z).color(colorTopRight.red(), colorTopRight.green(), colorTopRight.blue(), colorTopRight.alpha()).next();
        bufferBuilder.vertex(matrix, (float) xStart, (float) yStart, (float) z).color(colorTopLeft.red(), colorTopLeft.green(), colorTopLeft.blue(), colorTopLeft.alpha()).next();
        bufferBuilder.vertex(matrix, (float) xStart, (float) yEnd, (float) z).color(colorBottomLeft.red(), colorBottomLeft.green(), colorBottomLeft.blue(), colorBottomLeft.alpha()).next();
        bufferBuilder.vertex(matrix, (float) xEnd, (float) yEnd, (float) z).color(colorBottomRight.red(), colorBottomRight.green(), colorBottomRight.blue(), colorBottomRight.alpha()).next();
    }
}
