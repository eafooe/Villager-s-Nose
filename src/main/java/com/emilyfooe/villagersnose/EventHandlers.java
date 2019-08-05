package com.emilyfooe.villagersnose;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Consumer;

public class EventHandlers {
    @SubscribeEvent
    public static void shearNoseEvent(PlayerInteractEvent.EntityInteract event){
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ShearsItem){
            if (event.getTarget() instanceof VillagerEntity && event.getTarget().getEntityData().contains("hasNose")){
                boolean hasNose = event.getTarget().getEntityData().getBoolean("hasNose");
                VillagersNose.LOGGER.info("hasNose: " + hasNose);
                if (hasNose){
                    event.getTarget().getEntityData().putBoolean("hasNose", false);
                    ItemStack shears = event.getEntityPlayer().getHeldItemMainhand();
                    shears.damageItem(1, event.getEntityPlayer(), (Consumer<LivingEntity>) event.getTarget());
                }
            }
        }
    }

    @SubscribeEvent
    public static void addNoseBoolean(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof VillagerEntity){
            VillagerEntity villager = (VillagerEntity) event.getObject();
            if (!villager.getEntityData().contains("hasNose")){
                villager.getEntityData().putBoolean("hasNose", true);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent event){
        if (event.getEntity() instanceof VillagerEntity){
            VillagerEntity villager = (VillagerEntity) event.getEntity();
            if (villager.getEntityData().contains("hasNose")){
                if (!villager.getEntityData().getBoolean("hasNose")){
                    VillagersNose.LOGGER.info("Rendering w/o nose...");

                    //
                } else {
                    VillagersNose.LOGGER.info("Rendering w/ nose...");
                }
            }
        }
    }
}
