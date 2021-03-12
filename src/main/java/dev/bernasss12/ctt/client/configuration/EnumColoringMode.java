package dev.bernasss12.ctt.client.configuration;

import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;


public enum EnumColoringMode implements SelectionListEntry.Translatable {
    USER_DEFINED,
    ITEM_BASED,
    ORIGINAL;

    public static EnumColoringMode fromString(String coloringMode) {
        for (EnumColoringMode value : EnumColoringMode.values()) {
            if (value.toString().equals(coloringMode)) {
                return value;
            }
        }
        return ORIGINAL;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    @Override
    public String getKey() {
        return "enum.ctt.coloring_setting." + toString().toLowerCase();
    }
}

