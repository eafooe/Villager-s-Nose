package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.capabilities.INose;
import com.emilyfooe.villagersnose.capabilities.Nose;
import com.emilyfooe.villagersnose.capabilities.NoseStorage;
import com.emilyfooe.villagersnose.renderer.model.VillagerModelOverride;
import com.emilyfooe.villagersnose.renderer.model.VillagerRendererOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.renderable.RenderedImageFactory;

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
       Minecraft mc = Minecraft.getInstance();
       IReloadableResourceManager resourceManager = (IReloadableResourceManager) mc.getResourceManager();
       //TextureManager textureManager = mc.getTextureManager();
       //ItemRenderer item = mc.getItemRenderer();
       EntityRendererManager re = mc.getRenderManager();
       //EntityRendererManager r = new EntityRendererManager(textureManager, item, resourceManager);
       re.register(VillagerEntity.class, new VillagerRendererOverride(re, resourceManager));
       LOGGER.info("Client method registered");
    }
}
