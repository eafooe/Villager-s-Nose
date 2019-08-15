package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.capabilities.Nose.INose;
import com.emilyfooe.villagersnose.capabilities.Nose.Nose;
import com.emilyfooe.villagersnose.capabilities.Nose.NoseStorage;
import com.emilyfooe.villagersnose.client.overrides.OverrideVillagerRenderer;
import com.emilyfooe.villagersnose.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VillagersNose.MODID)
public class VillagersNose
{
    public static final String MODID = "villagersnose";
    public static final Logger LOGGER = LogManager.getLogger();

    public VillagersNose() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(EventHandlers.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(INose.class, new NoseStorage(), Nose::new);
        PacketHandler.register();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
       Minecraft mc = Minecraft.getInstance();
       IReloadableResourceManager resourceManager = (IReloadableResourceManager) mc.getResourceManager();
       EntityRendererManager re = mc.getRenderManager();
       re.register(VillagerEntity.class, new OverrideVillagerRenderer(re, resourceManager));
       LOGGER.info("Client method registered");
    }
}
