package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.capabilities.INose;
import com.emilyfooe.villagersnose.capabilities.NoseProvider;
import com.emilyfooe.villagersnose.item.ItemNose;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.emilyfooe.villagersnose.capabilities.NoseProvider.MY_CAPABILITY;
import static com.emilyfooe.villagersnose.capabilities.NoseProvider.MY_CAPABILITY_KEY;

public class EventHandlers {
    @SubscribeEvent
    public static void shearNoseEvent(PlayerInteractEvent.EntityInteract event){
        VillagersNose.LOGGER.info("PlayerInteractEvent.EntityInteract event fired.");
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ShearsItem){
            if (event.getTarget() instanceof VillagerEntity && event.getTarget().getCapability(MY_CAPABILITY).isPresent()){
                INose cap = event.getTarget().getCapability(MY_CAPABILITY).orElseThrow(() -> new RuntimeException("No inventory!"));
                boolean hasNose = cap.getHasNose();
                VillagersNose.LOGGER.info("hasNose: " + hasNose);
                if (hasNose){
                    cap.setHasNose(false);
                    ItemStack shears = event.getEntityPlayer().getHeldItemMainhand();
                    shears.damageItem(1, event.getEntityPlayer(), (exp) -> exp.sendBreakAnimation(event.getHand()));
                    event.getEntity().entityDropItem(ItemNose::new);
                }
            }
        }
    }

    @SubscribeEvent
    public static void addNoseBoolean(AttachCapabilitiesEvent<Entity> event){
        VillagersNose.LOGGER.info("AttachCapabilitiesEvent<Entity> event fired.");
        if (event.getObject() instanceof VillagerEntity){
            if (!event.getCapabilities().containsKey(MY_CAPABILITY_KEY)){
                try {
                    event.addCapability(MY_CAPABILITY_KEY, new NoseProvider());
                } catch (Exception ex){
                    VillagersNose.LOGGER.info("Capability not attached!!!");
                }
            }
        }
    }
}
