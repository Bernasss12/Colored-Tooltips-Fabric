package dev.bernasss12.ctt.client.configuration;

import dev.bernasss12.ctt.client.ColoredTooltipsClient;
import dev.bernasss12.ctt.client.util.Color;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Properties;


public class ModConfig {
    // Default settings
    public final EnumColoringMode defaultOutlineColoringMode = EnumColoringMode.ITEM_BASED;
    public final EnumColoringMode defaultBackgroundColoringMode = EnumColoringMode.ORIGINAL;
    public final float defaultOutlineOverallDarkeningFactor = 0.85f;
    public final float defaultOutlineTopToBottomDarkeningFactor = 0.85f;
    public final boolean defaultOutlineEnabled = true;
    public final float defaultBackgroundOverallDarkeningFactor = 0.15f;
    public final float defaultBackgroundTopToBottomDarkeningFactor = 0.85f;
    public final boolean defaultBackgroundOuterRing = true;
    public final Color defaultBackgroundColor = new Color(0xc5111111);
    public final Color defaultBrightColor = new Color(0xeee00000);
    public final Color defaultMidColor = new Color(0xee870000);
    public final Color defaultDarkColor = new Color(0xee5f0000);

    // Settings
    public EnumColoringMode outlineColoringMode;
    public float outlineOverallDarkeningFactor;
    public float outlineTopToBottomDarkeningFactor;
    public boolean outlineEnabled;
    public EnumColoringMode backgroundColoringMode;
    public float backgroundOverallDarkeningFactor;
    public float backgroundTopToBottomDarkeningFactor;
    public boolean backgroundOuterRing;

    public Color backgroundTopLeft;
    public Color backgroundTopRight;
    public Color backgroundBottomRight;
    public Color backgroundBottomLeft;
    public Color outlineTopLeft;
    public Color outlineTopRight;
    public Color outlineBottomRight;
    public Color outlineBottomLeft;

    public void save() {
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "colored_tooltips_config.properties");
        try {
            FileWriter writer = new FileWriter(file, false);
            Properties properties = new Properties();
            // Settings version tracking
            properties.setProperty("setting_version", 2 + "");
            // Outline Settings
            properties.setProperty("outline_color_mode", outlineColoringMode.toString());
            properties.setProperty("outline_overall_darken_factor", outlineOverallDarkeningFactor + "");
            properties.setProperty("outline_top_to_bottom_darken_factor", outlineTopToBottomDarkeningFactor + "");
            properties.setProperty("outline_enabled", outlineEnabled + "");
            // Background Settings
            properties.setProperty("background_color_mode", backgroundColoringMode.toString());
            properties.setProperty("background_overall_darken_factor", backgroundOverallDarkeningFactor + "");
            properties.setProperty("background_top_to_bottom_darken_factor", backgroundTopToBottomDarkeningFactor + "");
            properties.setProperty("background_outer_ring", backgroundOuterRing + "");
            // Color Settings
            properties.setProperty("color_outline_top_left", outlineTopLeft.toLiteralString());
            properties.setProperty("color_outline_top_right", outlineTopRight.toLiteralString());
            properties.setProperty("color_outline_bottom_right", outlineBottomRight.toLiteralString());
            properties.setProperty("color_outline_bottom_left", outlineBottomLeft.toLiteralString());
            properties.setProperty("color_background_top_left", backgroundTopLeft.toLiteralString());
            properties.setProperty("color_background_top_right", backgroundTopRight.toLiteralString());
            properties.setProperty("color_background_bottom_right", backgroundBottomRight.toLiteralString());
            properties.setProperty("color_background_bottom_left", backgroundBottomLeft.toLiteralString());
            properties.store(writer, null);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            defaultSettings();
        }
    }

    private void defaultSettings() {
        // Outline Settings
        outlineColoringMode = defaultOutlineColoringMode;
        outlineOverallDarkeningFactor = defaultOutlineOverallDarkeningFactor;
        outlineTopToBottomDarkeningFactor = defaultOutlineTopToBottomDarkeningFactor;
        outlineEnabled = defaultOutlineEnabled;

        // Background Settings
        backgroundColoringMode = defaultBackgroundColoringMode;
        backgroundOverallDarkeningFactor = defaultBackgroundOverallDarkeningFactor;
        backgroundTopToBottomDarkeningFactor = defaultBackgroundTopToBottomDarkeningFactor;
        backgroundOuterRing = defaultBackgroundOuterRing;

        // Default colors
        backgroundTopLeft = defaultBackgroundColor;
        backgroundTopRight = defaultBackgroundColor;
        backgroundBottomRight = defaultBackgroundColor;
        backgroundBottomLeft = defaultBackgroundColor;
        outlineTopLeft = defaultMidColor;
        outlineTopRight = defaultBrightColor;
        outlineBottomRight = defaultMidColor;
        outlineBottomLeft = defaultDarkColor;
    }

    public void load() {
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "colored_tooltips_config.properties");
        try {
            defaultSettings();
            if (!file.exists()) {
                ColoredTooltipsClient.logger().debug("Config created!");
                save();
                return;
            }
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            int configVersion = Integer.parseInt(properties.getProperty("setting_version", "1"));
            switch (configVersion) {
                case 1:
                    ColoredTooltipsClient.logger().warn("Old config detected, adapting to new format.");
                    loadVersionOne(properties);
                    break;
                case 2:
                    loadVersionTwo(properties);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            defaultSettings();
            try {
                Files.deleteIfExists(file.toPath());
            } catch (Exception ignored) {
            }
        }
        save();
    }

    /*
     *  Version 1 keys:
     *    outline_color_mode
     *    outline_color_value
     *    outline_color_darken_scale
     *    background_color_mode
     *    background_color_value
     *    background_color_darken_scale
     */
    private void loadVersionOne(Properties properties) {
        // Outline settings
        outlineOverallDarkeningFactor = defaultOutlineOverallDarkeningFactor;
        outlineColoringMode = EnumColoringMode.fromString(properties.getProperty("outline_color_mode"));
        outlineTopToBottomDarkeningFactor = Float.parseFloat(properties.getProperty("outline_color_darken_scale"));
        // Background settings
        backgroundOverallDarkeningFactor = defaultBackgroundOverallDarkeningFactor;
        backgroundColoringMode = EnumColoringMode.fromString(properties.getProperty("background_color_mode"));
        backgroundTopToBottomDarkeningFactor = Float.parseFloat(properties.getProperty("background_color_darken_scale"));
        // Colors
        outlineTopLeft = Color.fromLiteralString(properties.getProperty("outline_color_value"));
        outlineTopRight = outlineTopLeft;
        outlineBottomLeft = outlineTopLeft.darken(outlineTopToBottomDarkeningFactor);
        outlineBottomRight = outlineBottomLeft;
        backgroundTopRight = Color.fromLiteralString(properties.getProperty("background_color_value"));
        backgroundTopLeft = backgroundTopRight;
        backgroundBottomLeft = backgroundTopRight;
        backgroundBottomRight = backgroundTopRight;
    }

    /*
     *  Version 2 keys:
     *    outline_color_mode
     *    outline_overall_darken_factor
     *    outline_top_to_bottom_darken_factor
     *    background_color_mode
     *    background_overall_darken_factor
     *    background_top_to_bottom_darken_factor
     *    background_outer_ring
     *    color_outline_top_right
     *    color_outline_top_left
     *    color_outline_bottom_left
     *    color_outline_bottom_right
     *    color_background_top_right
     *    color_background_top_left
     *    color_background_bottom_left
     *    color_background_bottom_right
     */
    private void loadVersionTwo(Properties properties) {
        // Outline Settings
        outlineColoringMode = EnumColoringMode.fromString(properties.getProperty("outline_color_mode"));
        outlineOverallDarkeningFactor = Float.parseFloat(properties.getProperty("outline_overall_darken_factor"));
        outlineTopToBottomDarkeningFactor = Float.parseFloat(properties.getProperty("outline_top_to_bottom_darken_factor"));
        outlineEnabled = Boolean.getBoolean(properties.getProperty("outline_enabled", defaultOutlineEnabled + ""));
        // Background Settings
        backgroundColoringMode = EnumColoringMode.fromString(properties.getProperty("background_color_mode"));
        backgroundOverallDarkeningFactor = Float.parseFloat(properties.getProperty("background_overall_darken_factor"));
        backgroundTopToBottomDarkeningFactor = Float.parseFloat(properties.getProperty("background_top_to_bottom_darken_factor"));
        backgroundOuterRing = Boolean.getBoolean(properties.getProperty("background_outer_ring", defaultBackgroundOuterRing + ""));
        // Colors
        outlineTopRight = Color.fromLiteralString(properties.getProperty("color_outline_top_right"));
        outlineTopLeft = Color.fromLiteralString(properties.getProperty("color_outline_top_left"));
        outlineBottomLeft = Color.fromLiteralString(properties.getProperty("color_outline_bottom_left"));
        outlineBottomRight = Color.fromLiteralString(properties.getProperty("color_outline_bottom_right"));
        backgroundTopRight = Color.fromLiteralString(properties.getProperty("color_background_top_right"));
        backgroundTopLeft = Color.fromLiteralString(properties.getProperty("color_background_top_left"));
        backgroundBottomLeft = Color.fromLiteralString(properties.getProperty("color_background_bottom_left"));
        backgroundBottomRight = Color.fromLiteralString(properties.getProperty("color_background_bottom_right"));
    }

}
