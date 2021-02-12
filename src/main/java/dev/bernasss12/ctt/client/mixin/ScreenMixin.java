package dev.bernasss12.ctt.client.mixin;

import dev.bernasss12.ctt.client.ColoredTooltipsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
@Environment(EnvType.CLIENT)
public abstract class ScreenMixin extends DrawableHelper {

    @Inject(at = @At(value = "HEAD"),
            method = "Lnet/minecraft/client/gui/screen/Screen;renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V")
    private void appendRenderTooltipHead(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo info) {
        ColoredTooltipsClient.currentNameColor.set(stack.getRarity().formatting.getColorValue());
    }

    @Inject(at = @At(value = "TAIL"),
            method = "Lnet/minecraft/client/gui/screen/Screen;renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V")
    private void appendRenderTooltipTail(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo info) {
        ColoredTooltipsClient.currentNameColor.set(Rarity.COMMON.formatting.getColorValue());
    }

    @ModifyConstant(method = "renderOrderedTooltip", constant = @Constant(intValue = -267386864))
    private int modifyBgColor(int originalBgColor) {
        return ColoredTooltipsClient.getBgColor(originalBgColor);
    }

    @ModifyConstant(method = "renderOrderedTooltip", constant = @Constant(intValue = 1347420415))
    private int modifyTopColor(int originalTopColor) {
        return ColoredTooltipsClient.getTopColor(originalTopColor);
    }

    @ModifyConstant(method = "renderOrderedTooltip", constant = @Constant(intValue = 1344798847))
    private int modifyBottomColor(int originalBotColor) {
        return ColoredTooltipsClient.getBottomColor(originalBotColor);
    }
}
