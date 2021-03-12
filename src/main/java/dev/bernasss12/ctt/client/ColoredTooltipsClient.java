package dev.bernasss12.ctt.client;

import dev.bernasss12.ctt.client.configuration.ModConfig;
import dev.bernasss12.ctt.client.util.Color;
import dev.bernasss12.ctt.client.util.ColorQuartet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class ColoredTooltipsClient implements ClientModInitializer {

    public static ThreadLocal<Color> currentNameColor;

    private static Logger logger;
    private static ModConfig modConfig;


    public static Logger logger() {
        if (logger == null) {
            logger = LogManager.getLogger("Colored-Tooltips");
        }
        return logger;
    }

    public static ModConfig config() {
        if (modConfig == null) {
            modConfig = new ModConfig();
            modConfig.load();
        }
        return modConfig;
    }

    public static ColorQuartet getBackgroundColor(int color) {
        switch (modConfig.backgroundColoringMode) {
            case ITEM_BASED:
                return new ColorQuartet(currentNameColor.get())
                        .darken(config().backgroundOverallDarkeningFactor)
                        .darkenBottom(config().backgroundTopToBottomDarkeningFactor);
            case USER_DEFINED:
                return new ColorQuartet(
                        config().backgroundTopRight,
                        config().backgroundTopLeft,
                        config().backgroundBottomLeft,
                        config().backgroundBottomRight
                );
            case ORIGINAL:
            default:
                return new ColorQuartet(new Color(color));
        }
    }

    public static ColorQuartet getOutlineColors(ColorQuartet original) {
        switch (modConfig.outlineColoringMode) {
            case ITEM_BASED:
                return new ColorQuartet(currentNameColor.get())
                        .darken(config().outlineOverallDarkeningFactor)
                        .darkenBottom(config().outlineTopToBottomDarkeningFactor);
            case USER_DEFINED:
                return new ColorQuartet(
                        config().outlineTopRight,
                        config().outlineTopLeft,
                        config().outlineBottomLeft,
                        config().outlineBottomRight
                );
            case ORIGINAL:
            default:
                return original;
        }
    }

    @Override
    public void onInitializeClient() {
        logger();
        config();
        currentNameColor = new ThreadLocal<>();
        currentNameColor.set(Color.WHITE);
    }
}
