package dev.bernasss12.ctt.client.mixin;

import dev.bernasss12.ctt.client.ColoredTooltipsClient;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ScreenMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
        ColoredTooltipsClient.isArchitecturyPresent = FabricLoader.getInstance().isModLoaded(ColoredTooltipsClient.ARCHITECTURY_MODID);
        if (ColoredTooltipsClient.isArchitecturyPresent)
            ColoredTooltipsClient.logger().error("Architectury is currently loaded and integration is still not implemented. This mod will disable itself.");
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true; // !ColoredTooltipsClient.isArchitecturyPresent;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

}
