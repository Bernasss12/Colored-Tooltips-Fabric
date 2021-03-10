package dev.bernasss12.ctt.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class ColoredTooltipsClient implements ClientModInitializer {

    public static final String ARCHITECTURY_MODID = "architectury";
    public static boolean isArchitecturyPresent = false;

    public static ThreadLocal<Integer> currentNameColor;

    public static Logger logger;

    public static Logger logger() {
        if (logger == null) {
            logger = LogManager.getLogger("Colored-Tooltips");
        }
        return logger;
    }

    public static int getBgColor(int original) {
        switch (ModConfig.backgroundColoringMode) {
            case ITEM_BASED:
                return makeOpaque(darkenColor(currentNameColor.get(), ModConfig.backgroundDarkeningFactor));
            case USER_DEFINED:
                return ModConfig.backgroundColor;
            case ORIGINAL:
            default:
                return original;
        }
    }

    @Override
    public void onInitializeClient() {
        logger = logger();
        ModConfig.loadConfig();
        currentNameColor = new ThreadLocal<>();
        currentNameColor.set(0);
    }

    public static int getTopColor(int original) {
        switch (ModConfig.outlineColoringMode) {
            case ITEM_BASED:
                return makeOpaque(currentNameColor.get());
            case USER_DEFINED:
                return makeOpaque(ModConfig.outlineColor);
            case ORIGINAL:
            default:
                return original;
        }
    }

    public static int getBottomColor(int original){
        switch (ModConfig.outlineColoringMode){
            case ITEM_BASED:
                return makeOpaque(darkenColor(currentNameColor.get(), ModConfig.outlineDarkeningFactor));
            case USER_DEFINED:
                return makeOpaque(darkenColor(ModConfig.outlineColor, ModConfig.outlineDarkeningFactor));
            case ORIGINAL:
            default:
                return original;
        }
    }

    /**
     * Prepends 255 in the alpha channel of the color.
     * */
    private static int makeOpaque(int color){
        return 0xff000000 | color;
    }

    /**
     * Darkens the given color by the given factor.
     * @param color color to be manipulated
     * @param darkeningFactor value to multiply the color with, should be in between 0.1 and 1.0;
     * */
    private static int darkenColor(int color, float darkeningFactor){
        if(darkeningFactor > 1.0f) darkeningFactor = 1.0f;
        else if(darkeningFactor < 0.1f) darkeningFactor = 0.1f;
        int alpha = ((color & 0xff000000) >> 24);
        int red = (int)(((color & 0x00ff0000) >> 16) * darkeningFactor);
        int green = (int)(((color & 0x0000ff00) >> 8) * darkeningFactor);
        int blue = (int)(((color & 0x000000ff)) * darkeningFactor);
        color = alpha << 24 | red << 16 | green << 8 | blue;
        return color;
    }
}
