package dev.bernasss12.ctt.client;

import dev.bernasss12.ctt.client.configuration.ModConfigScreen;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> ModConfigScreen.getConfigScreen().build();
    }
}
