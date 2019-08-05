package com.emilyfooe.villagersnose;

import net.minecraft.item.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
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
    static final Logger LOGGER = LogManager.getLogger();
    public static ArmorMaterial materialNose;

    public VillagersNose() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setup method registered");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client method registered");
    }
}
