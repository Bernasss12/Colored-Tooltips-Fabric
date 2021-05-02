package dev.bernasss12.ctt.client.configuration;

import dev.bernasss12.ctt.client.ColoredTooltipsClient;
import dev.bernasss12.ctt.client.util.Color;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ModConfigScreen {

    public static ConfigBuilder getConfigScreen() {
        ConfigBuilder builder = ConfigBuilder.create();
        ModConfig config = ColoredTooltipsClient.config();
        builder.setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/oak_planks.png"));
        builder.setGlobalized(true);
        builder.setTitle(new TranslatableText("screen.ctt.settings_title"));
        //if (!ColoredTooltipsClient.isArchitecturyPresent) {
        putNormalSettings(builder, config);
        //}
        builder.setSavingRunnable(config::save);
        return builder;
    }

    private static void putNormalSettings(ConfigBuilder builder, ModConfig config) {
        ConfigCategory outline = builder.getOrCreateCategory(new TranslatableText("category.ctt.outline_settings"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        putNormalOutlineSettings(outline, entryBuilder, config);
        ConfigCategory background = builder.getOrCreateCategory(new TranslatableText("category.ctt.background_settings"));
        putNormalBackgroundSettings(background, entryBuilder, config);
    }

    private static void putNormalOutlineSettings(ConfigCategory category, ConfigEntryBuilder entryBuilder, ModConfig config) {
        category.addEntry(entryBuilder.startEnumSelector(new TranslatableText("entry.ctt.general.coloring_mode"), EnumColoringMode.class, config.outlineColoringMode)
                .setDefaultValue(config.defaultOutlineColoringMode)
                .setSaveConsumer(setting -> config.outlineColoringMode = setting)
                .setTooltip(
                        new TranslatableText("entry.ctt.general.coloring_mode.tooltip"),
                        new TranslatableText("entry.ctt.general.coloring_mode.tooltip1"),
                        new TranslatableText("entry.ctt.general.coloring_mode.tooltip2"),
                        new TranslatableText("entry.ctt.general.coloring_mode.tooltip3")
                )
                .build());
        category.addEntry(entryBuilder.startFloatField(new TranslatableText("entry.ctt.general.overall_darkening_factor"), config.outlineOverallDarkeningFactor)
                .setDefaultValue(config.defaultOutlineOverallDarkeningFactor)
                .setSaveConsumer(factor -> config.outlineOverallDarkeningFactor = factor)
                .setTooltip(new TranslatableText("entry.ctt.general.overall_darkening_factor.tooltip"))
                .setMin(0.1f)
                .setMax(1.0f)
                .build());
        category.addEntry(entryBuilder.startFloatField(new TranslatableText("entry.ctt.general.top_to_bottom_darkening_factor"), config.outlineTopToBottomDarkeningFactor)
                .setDefaultValue(config.defaultOutlineTopToBottomDarkeningFactor)
                .setSaveConsumer(factor -> config.outlineTopToBottomDarkeningFactor = factor)
                .setTooltip(new TranslatableText("entry.ctt.general.top_to_bottom_darkening_factor.tooltip"))
                .setMin(0.1f)
                .setMax(1.0f)
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ctt.general.outline_enabled"), config.outlineEnabled)
                .setDefaultValue(config.outlineEnabled)
                .setSaveConsumer(outlineEnabled -> config.outlineEnabled = outlineEnabled)
                .setTooltip(new TranslatableText("entry.ctt.general.outline_enabled.tooltip"))
                .build());
        category.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.general.top_right"), config.outlineTopRight.getColor())
                .setDefaultValue(config.defaultMidColor.getColor())
                .setSaveConsumer(color -> config.outlineTopRight = new Color(color))
                .build());
        category.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.general.top_left"), config.outlineTopLeft.getColor())
                .setDefaultValue(config.defaultBrightColor.getColor())
                .setSaveConsumer(color -> config.outlineTopLeft = new Color(color))
                .build());
        category.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.general.bottom_left"), config.outlineBottomLeft.getColor())
                .setDefaultValue(config.defaultMidColor.getColor())
                .setSaveConsumer(color -> config.outlineBottomLeft = new Color(color))
                .build());
        category.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.general.bottom_right"), config.outlineBottomRight.getColor())
                .setDefaultValue(config.defaultDarkColor.getColor())
                .setSaveConsumer(color -> config.outlineBottomRight = new Color(color))
                .build());
    }

    private static void putNormalBackgroundSettings(ConfigCategory category, ConfigEntryBuilder entryBuilder, ModConfig config) {
        category.addEntry(entryBuilder.startEnumSelector(new TranslatableText("entry.ctt.general.coloring_mode"), EnumColoringMode.class, config.backgroundColoringMode)
                .setDefaultValue(config.defaultBackgroundColoringMode)
                .setSaveConsumer(setting -> config.backgroundColoringMode = setting)
                .setTooltip(
                        new TranslatableText("entry.ctt.general.coloring_mode.tooltip"),
                        new TranslatableText("entry.ctt.general.coloring_mode.tooltip1"),
                        new TranslatableText("entry.ctt.general.coloring_mode.tooltip2"),
                        new TranslatableText("entry.ctt.general.coloring_mode.tooltip3")
                )
                .build());
        category.addEntry(entryBuilder.startFloatField(new TranslatableText("entry.ctt.general.overall_darkening_factor"), config.backgroundOverallDarkeningFactor)
                .setDefaultValue(config.defaultBackgroundOverallDarkeningFactor)
                .setSaveConsumer(factor -> config.backgroundOverallDarkeningFactor = factor)
                .setTooltip(new TranslatableText("entry.ctt.general.overall_darkening_factor.tooltip"))
                .setMin(0.1f)
                .setMax(1.0f)
                .build());
        category.addEntry(entryBuilder.startFloatField(new TranslatableText("entry.ctt.general.top_to_bottom_darkening_factor"), config.backgroundTopToBottomDarkeningFactor)
                .setDefaultValue(config.defaultBackgroundTopToBottomDarkeningFactor)
                .setSaveConsumer(factor -> config.backgroundTopToBottomDarkeningFactor = factor)
                .setTooltip(new TranslatableText("entry.ctt.general.top_to_bottom_darkening_factor.tooltip"))
                .setMin(0.1f)
                .setMax(1.0f)
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ctt.general.background_outer_ring"), config.backgroundOuterRing)
                .setDefaultValue(config.backgroundOuterRing)
                .setSaveConsumer(backgroundOuterRing -> config.backgroundOuterRing = backgroundOuterRing)
                .setTooltip(new TranslatableText("entry.ctt.general.background_outer_ring.tooltip"))
                .build());
        category.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.general.top_right"), config.backgroundTopRight.getColor())
                .setDefaultValue(config.defaultBackgroundColor.getColor())
                .setSaveConsumer(color -> config.backgroundTopRight = new Color(color))
                .build());
        category.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.general.top_left"), config.backgroundTopLeft.getColor())
                .setDefaultValue(config.defaultBackgroundColor.getColor())
                .setSaveConsumer(color -> config.backgroundTopLeft = new Color(color))
                .build());
        category.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.general.bottom_left"), config.backgroundBottomLeft.getColor())
                .setDefaultValue(config.defaultBackgroundColor.getColor())
                .setSaveConsumer(color -> config.backgroundBottomLeft = new Color(color))
                .build());
        category.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("entry.ctt.general.bottom_right"), config.backgroundBottomRight.getColor())
                .setDefaultValue(config.defaultBackgroundColor.getColor())
                .setSaveConsumer(color -> config.backgroundBottomRight = new Color(color))
                .build());
    }

}
