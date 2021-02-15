package dev.bernasss12.ctt.client;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Properties;

public class ModConfig {

    // Outline Settings
    private static final ColoringMode DEFAULT_OUTLINE_COLORING_MODE = ColoringMode.ITEM_BASED;
    protected static ColoringMode outlineColoringMode;
    private static final int DEFAULT_OUTLINE_COLOR = 0x5000ff;
    protected static int outlineColor;
    private static final float DEFAULT_OUTLINE_DARKENING_FACTOR = 0.85f;
    protected static float outlineDarkeningFactor;
    // Background Settings
    private static final ColoringMode DEFAULT_BACKGROUND_COLORING_MODE = ColoringMode.ORIGINAL;
    protected static ColoringMode backgroundColoringMode;
    private static final int DEFAULT_BACKGROUND_COLOR = -267386864;
    protected static int backgroundColor;
    private static final float DEFAULT_BACKGROUND_DARKENING_FACTOR = 0.15f;
    protected static float backgroundDarkeningFactor;

    private static void defaultSettings() {
        // Outline Settings
        outlineColoringMode = DEFAULT_OUTLINE_COLORING_MODE;
        outlineColor = DEFAULT_OUTLINE_COLOR;
        outlineDarkeningFactor = DEFAULT_OUTLINE_DARKENING_FACTOR;
        // Background Settings
        backgroundColoringMode = DEFAULT_BACKGROUND_COLORING_MODE;
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
        backgroundDarkeningFactor = DEFAULT_BACKGROUND_DARKENING_FACTOR;
    }

    public static void loadConfig() {
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "colored_tooltips_config.properties");
        try {
            if (file.getParentFile().mkdirs()) System.out.println("[ColoredTooltips] Config created!");
            defaultSettings();
            if (!file.exists()) {
                saveConfig();
            }
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            // Outline Settings
            outlineColoringMode = ColoringMode.fromString(properties.getProperty("outline_color_mode"));
            outlineColor = Integer.parseInt(properties.getProperty("outline_color_value"));
            outlineDarkeningFactor = Float.parseFloat(properties.getProperty("outline_color_darken_scale"));
            // Background Settings
            backgroundColoringMode = ColoringMode.fromString(properties.getProperty("background_color_mode"));
            backgroundColor = Integer.parseInt(properties.getProperty("background_color_value"));
            backgroundDarkeningFactor = Float.parseFloat(properties.getProperty("background_color_darken_scale"));
            saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
            defaultSettings();
            try {
                Files.deleteIfExists(file.toPath());
            } catch (Exception ignored) {
            }
        }
        saveConfig();
    }

    public static void saveConfig() {
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "colored_tooltips_config.properties");
        try {
            FileWriter writer = new FileWriter(file, false);
            Properties properties = new Properties();
            // Outline Settings
            properties.setProperty("outline_color_mode", outlineColoringMode.toString());
            properties.setProperty("outline_color_value", outlineColor + "");
            properties.setProperty("outline_color_darken_scale", outlineDarkeningFactor + "");
            // Background Settings
            properties.setProperty("background_color_mode", backgroundColoringMode.toString());
            properties.setProperty("background_color_value", backgroundColor + "");
            properties.setProperty("background_color_darken_scale", backgroundDarkeningFactor + "");
            properties.store(writer, null);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            defaultSettings();
        }
    }

    public static ConfigBuilder getConfigScreen() {
        ConfigBuilder builder = ConfigBuilder.create();
        builder.setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/oak_planks.png"));
        builder.setGlobalized(true);
        builder.setTitle(new TranslatableText("screen.ctt.settings_title"));
        ConfigCategory outline = builder.getOrCreateCategory(new TranslatableText("category.ctt.outline_settings"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        outline.addEntry(entryBuilder.startEnumSelector(new TranslatableText("entry.ctt.outline_settings.coloring_mode"), ColoringMode.class, outlineColoringMode)
                .setDefaultValue(DEFAULT_OUTLINE_COLORING_MODE)
                .setSaveConsumer(setting -> outlineColoringMode = setting).build());
        outline.addEntry(entryBuilder.startColorField(new TranslatableText("entry.ctt.outline_settings.color"), outlineColor)
                .setDefaultValue(DEFAULT_OUTLINE_COLOR)
                .setSaveConsumer(color -> outlineColor = color)
                .build());
        outline.addEntry(entryBuilder.startFloatField(new TranslatableText("entry.ctt.outline_settings.bottom_color_scaling"), outlineDarkeningFactor)
                .setDefaultValue(DEFAULT_OUTLINE_DARKENING_FACTOR)
                .setSaveConsumer(factor -> outlineDarkeningFactor = factor)
                .setMin(0.1f)
                .setMax(1.0f)
                .build());
        ConfigCategory background = builder.getOrCreateCategory(new TranslatableText("category.ctt.background_settings"));
        background.addEntry(entryBuilder.startEnumSelector(new TranslatableText("entry.ctt.background_settings.coloring_mode"), ColoringMode.class, backgroundColoringMode)
                .setDefaultValue(DEFAULT_BACKGROUND_COLORING_MODE)
                .setSaveConsumer(setting -> backgroundColoringMode = setting)
                .build());
        background.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.background_settings.color"), backgroundColor)
                .setDefaultValue(DEFAULT_BACKGROUND_COLOR)
                .setSaveConsumer(color -> backgroundColor = color)
                .build());
        background.addEntry(entryBuilder.startFloatField(new TranslatableText("entry.ctt.background_settings.color_scaling"), backgroundDarkeningFactor)
                .setDefaultValue(DEFAULT_BACKGROUND_DARKENING_FACTOR)
                .setSaveConsumer(factor -> backgroundDarkeningFactor = factor)
                .setMin(0.1f)
                .setMax(1.0f)
                .build());
        builder.setSavingRunnable(() -> {
            saveConfig();
            loadConfig();
        });
        return builder;
    }

    protected enum ColoringMode implements SelectionListEntry.Translatable {
        USER_DEFINED,
        ITEM_BASED,
        ORIGINAL;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

        public static ColoringMode fromString(String coloringMode) {
            for (ColoringMode value : ColoringMode.values()) {
                if (value.toString().equals(coloringMode)) {
                    return value;
                }
            }
            return ORIGINAL;
        }

        @Override
        public String getKey() {
            return "enum.ctt.coloring_setting." + toString().toLowerCase();
        }
    }
}
