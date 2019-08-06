package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.capabilities.INose;
import com.emilyfooe.villagersnose.capabilities.Nose;
import com.emilyfooe.villagersnose.capabilities.NoseStorage;
import com.emilyfooe.villagersnose.renderer.model.VillagerRendererOverride;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VillagersNose.MODID)
public class VillagersNose
{
    // Directly reference a log4j logger.
    public static final String MODID = "villagersnose";
    public static final Logger LOGGER = LogManager.getLogger();

    public VillagersNose() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(EventHandlers.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(INose.class, new NoseStorage(), Nose::new);
        LOGGER.info("Setup method registered");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(VillagerEntity.class, VillagerRendererOverride::new);
        LOGGER.info("Client method registered");
    }
}
