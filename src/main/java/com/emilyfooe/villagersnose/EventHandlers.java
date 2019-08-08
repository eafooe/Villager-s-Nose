package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.capabilities.Nose.INose;
import com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider;
import com.emilyfooe.villagersnose.capabilities.Timer.ITimer;
import com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider;
import com.emilyfooe.villagersnose.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;
import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP_KEY;
import static com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider.TIMER_CAP;
import static com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider.TIMER_CAP_KEY;

public class EventHandlers {
    private static int ticksPerSecond = 20;
    private static int regrowthTime = Configuration.COMMON.regrowthTime.get() * ticksPerSecond;

    // If a villager entity does not have a nose, decrement the nose regrowth timer until it hits zero.
    // When the timer hits zero, regrow the villager's nose.
    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event){
        if (event.getEntityLiving() instanceof VillagerEntity){
            VillagerEntity villager = (VillagerEntity) event.getEntityLiving();
            if (villager.getCapability(NOSE_CAP).isPresent() && villager.getCapability(TIMER_CAP).isPresent()){
                INose noseCap = event.getEntityLiving().getCapability(NOSE_CAP).orElseThrow(() -> new RuntimeException("No inventory!"));
                ITimer timerCap = event.getEntityLiving().getCapability(TIMER_CAP).orElseThrow(() -> new RuntimeException("No timer!"));
                if (!noseCap.getHasNose()){
                    if (timerCap.getTimer() > 0){
                        timerCap.decrementTimer();
                    } else {
                        noseCap.setHasNose(true);
                    }
                }
            }
        }
    }

    // If a player entity right-clicks a villager entity with a nose while holding shears, remove the villager's nose
    // Drop the nose as an item, damage the shears, and set a timer so the nose regrows after a set time
    @SubscribeEvent
    public static void shearNoseEvent(PlayerInteractEvent.EntityInteract event){
        VillagersNose.LOGGER.info("PlayerInteractEvent.EntityInteract event fired.");
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ShearsItem){
            if (event.getTarget() instanceof VillagerEntity && event.getTarget().getCapability(NOSE_CAP).isPresent()){
                INose noseCapability = event.getTarget().getCapability(NOSE_CAP).orElseThrow(() -> new RuntimeException("No inventory!"));
                boolean hasNose = noseCapability.getHasNose();
                ITimer timerCapability = event.getTarget().getCapability(TIMER_CAP).orElseThrow(() -> new RuntimeException("No timer!"));
                VillagersNose.LOGGER.info("hasNose: " + hasNose);
                if (hasNose){
                    noseCapability.setHasNose(false);
                    timerCapability.setTimer(regrowthTime);
                    ItemStack shears = event.getEntityPlayer().getHeldItemMainhand();
                    shears.damageItem(1, event.getEntityPlayer(), (exp) -> exp.sendBreakAnimation(event.getHand()));
                    event.getEntity().entityDropItem(ModItems.ITEM_NOSE);
                }
            }
        }
    }

    // Add a nose and timer capability to villager entities
    @SubscribeEvent
    public static void addNoseBoolean(AttachCapabilitiesEvent<Entity> event){
        VillagersNose.LOGGER.info("AttachCapabilitiesEvent<Entity> event fired.");
        if (event.getObject() instanceof VillagerEntity){
            if (!event.getCapabilities().containsKey(NOSE_CAP_KEY)){
                try {
                    event.addCapability(NOSE_CAP_KEY, new NoseProvider());
                } catch (Exception ex){
                    VillagersNose.LOGGER.info("Capability not attached!!!");
                }
            }
            if (!event.getCapabilities().containsKey(TIMER_CAP_KEY)){
                try {
                    event.addCapability(TIMER_CAP_KEY, new TimerProvider());
                } catch (Exception ex){
                    VillagersNose.LOGGER.info("Capability not attached!!!");
                }
            }
        }
    }
}
