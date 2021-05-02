package dev.bernasss12.ctt.client.mixin;

import dev.bernasss12.ctt.client.ColoredTooltipsClient;
import dev.bernasss12.ctt.client.configuration.EnumColoringMode;
import dev.bernasss12.ctt.client.util.Color;
import dev.bernasss12.ctt.client.util.ColorQuartet;
import dev.bernasss12.ctt.client.util.DrawingHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(Screen.class)
@Environment(EnvType.CLIENT)
public abstract class ScreenMixin extends DrawableHelper {

    @Inject(at = @At(value = "HEAD"),
            method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V")
    private void setCurrentItemBasedColorIfNecessary(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo info) {
        if (ColoredTooltipsClient.config().outlineColoringMode == EnumColoringMode.ITEM_BASED || ColoredTooltipsClient.config().backgroundColoringMode == EnumColoringMode.ITEM_BASED) {
            Formatting formatting = stack.getRarity().formatting;
            if (formatting.isColor()) {
                ColoredTooltipsClient.currentNameColor.set(new Color(formatting.getColorValue() != null ? formatting.getColorValue() : Color.WHITE.getColor()).getOpaque());
            }
        }
    }

    @Inject(at = @At(value = "TAIL"),
            method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V")
    private void resetCurrentItemBasedColorIfNecessary(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo info) {
        if (ColoredTooltipsClient.config().outlineColoringMode == EnumColoringMode.ITEM_BASED || ColoredTooltipsClient.config().backgroundColoringMode == EnumColoringMode.ITEM_BASED) {
            ColoredTooltipsClient.currentNameColor.set(Color.WHITE);
        }
    }

    /*
     * Ignores all fillGradient calls so I can replace them with an equivalent set of calls but with variable colors.
     */
    @Redirect(method = "renderOrderedTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;fillGradient(Lnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/BufferBuilder;IIIIIII)V"))
    private void ignoreFillGradient(Matrix4f matrix, BufferBuilder bufferBuilder, int xStart, int yStart, int xEnd, int yEnd, int z, int colorStart, int colorEnd) {
    }


    /**
     * @param lines text inside the tooltip
     * @param x     level where the tooltip should be drawn at
     * @param y     level where the tooltip should be drawn at
     * @param i     calculated tooltip width
     * @param k     x level where the tooltip will be drawn at
     * @param l     y level where the tooltip will be drawn at
     * @param m     calculated tooltip width (same as i?)
     * @param n     calculated tooltip height
     * @param o     vanilla tooltip background color.
     * @param p     vanilla tooltip outline color (top).
     * @param q     vanilla tooltip outline color (bottom).
     * @param r     z level where the tooltip will be drawn at
     */
    @Inject(method = "renderOrderedTooltip", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/util/math/MatrixStack$Entry;getModel()Lnet/minecraft/util/math/Matrix4f;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void injectTooltipBody(
            MatrixStack matrices,
            List<? extends OrderedText> lines,
            int x,
            int y,
            CallbackInfo ci,
            int i,
            int k,
            int l,
            int m,
            int n,
            int o,
            int p,
            int q,
            int r,
            Tessellator tessellator,
            BufferBuilder bufferBuilder,
            Matrix4f matrix4f
    ) {
        // Background outline drawing.
        ColorQuartet background = ColoredTooltipsClient.getBackgroundColor(o);
        if (ColoredTooltipsClient.config().backgroundOuterRing) {
            DrawingHelper.fillHorizontalGradient(matrix4f, bufferBuilder, k - 3, l - 4, k + i + 3, l - 3, r, background.topLeft, background.topRight);
            DrawingHelper.fillHorizontalGradient(matrix4f, bufferBuilder, k - 3, l + n + 3, k + i + 3, l + n + 4, 400, background.bottomLeft, background.bottomRight);
            DrawingHelper.fillVerticalGradient(matrix4f, bufferBuilder, k - 4, l - 3, k - 3, l + n + 3, r, background.topLeft, background.bottomLeft);
            DrawingHelper.fillVerticalGradient(matrix4f, bufferBuilder, k + i + 3, l - 3, k + i + 4, l + n + 3, r, background.topRight, background.bottomRight);
        }
        DrawingHelper.fillGradient(matrix4f, bufferBuilder, k - 3, l - 3, k + i + 3, l + n + 3, r, background.topRight, background.topLeft, background.bottomLeft, background.bottomRight);
        // Outline drawing.
        if (ColoredTooltipsClient.config().outlineEnabled) {
            ColorQuartet outline = ColoredTooltipsClient.getOutlineColors(new ColorQuartet(new Color(p), new Color(q)));
            DrawingHelper.fillVerticalGradient(matrix4f, bufferBuilder, k - 3, l - 3 + 1, k - 3 + 1, l + n + 3 - 1, 400, outline.topLeft, outline.bottomLeft);
            DrawingHelper.fillVerticalGradient(matrix4f, bufferBuilder, k + i + 2, l - 3 + 1, k + i + 3, l + n + 3 - 1, 400, outline.topRight, outline.bottomRight);
            DrawingHelper.fillHorizontalGradient(matrix4f, bufferBuilder, k - 3, l - 3, k + i + 3, l - 3 + 1, 400, outline.topLeft, outline.topRight);
            DrawingHelper.fillHorizontalGradient(matrix4f, bufferBuilder, k - 3, l + n + 2, k + i + 3, l + n + 3, 400, outline.bottomLeft, outline.bottomRight);
        }
    }
}
