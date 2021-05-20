package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.capabilities.Nose.INose;
import com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider;
import com.emilyfooe.villagersnose.capabilities.Timer.ITimer;
import com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider;
import com.emilyfooe.villagersnose.init.ModItems;
import com.emilyfooe.villagersnose.network.ClientPacket;
import com.emilyfooe.villagersnose.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Arrays;
import java.util.List;

import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;
import static com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider.TIMER_CAP;

public class EventHandlers {
    private static int ticksPerSecond = 20;
    private static int regrowthTime = Configuration.COMMON.regrowthTime.get() * ticksPerSecond;
    private static boolean noseRegenerates = Configuration.COMMON.noseRegenerates.get();
    private static final List shearable = Arrays.asList(WitchEntity.class, VillagerEntity.class, WanderingTraderEntity.class);

    // Add a nose and timer capability to nose-shearable instances
    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (shearable.contains(event.getObject().getClass())){
            event.addCapability(NoseProvider.IDENTIFIER, new NoseProvider());
            event.addCapability(TimerProvider.IDENTIFIER, new TimerProvider());
        }
    }

    private static boolean entityTypeIsShearable(Entity entity){
        return shearable.contains(entity.getClass());
    }
    // If the player reduces the nose regrowth time in the config file, update these changes
    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinWorldEvent event){
        if (entityTypeIsShearable(event.getEntity())){
            ITimer timerCap = event.getEntity().getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
            if (timerCap.getTimer() > regrowthTime){
                timerCap.setTimer(regrowthTime);
            }
        }
    }

    // When a new client starts viewing the entity, notify it of existing data
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        if (player instanceof ServerPlayerEntity && entityTypeIsShearable(target)) {
            PacketDistributor.PacketTarget dest = PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player);
            boolean hasNose = target.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new).hasNose();
            int entityId = target.getId();
            PacketHandler.INSTANCE.send(dest, new ClientPacket(entityId, hasNose));
        }


    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving().getCommandSenderWorld().isClientSide){
            return;
        }
        if (entityTypeIsShearable(event.getEntityLiving()) && noseRegenerates) {
            INose noseCap = event.getEntityLiving().getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
            if (!noseCap.hasNose()) {
                ITimer timerCap = event.getEntityLiving().getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
                if (timerCap.getTimer() > 0) {
                    timerCap.decrementTimer();
                } else {
                    noseCap.setHasNose(true);
                    PacketDistributor.PacketTarget dest = PacketDistributor.TRACKING_ENTITY.with(event::getEntityLiving);
                    int entityId = event.getEntityLiving().getId();
                    PacketHandler.INSTANCE.send(dest, new ClientPacket(entityId, true));
                }
            }
        }
    }



@SubscribeEvent
public static void shearWitchNoseEvent(PlayerInteractEvent.EntityInteractSpecific event){
        if (!(event.getTarget() instanceof WitchEntity)){
            return;
        }
    if (!(event.getPlayer() instanceof ServerPlayerEntity && event.getHand() == Hand.MAIN_HAND)) {
        return;
    }


        WitchEntity witch = (WitchEntity) event.getTarget();
        if (!witch.getCapability(NOSE_CAP).isPresent()){
            return;
        }
        INose capability = witch.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
        if (capability.hasNose() && isHoldingShears(event.getPlayer())){
            capability.setHasNose(false);
            event.getTarget().playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
            ITimer timerCap = witch.getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
            timerCap.setTimer(regrowthTime);
            PacketDistributor.PacketTarget dest = PacketDistributor.TRACKING_ENTITY.with(event::getTarget);
            PacketHandler.INSTANCE.send(dest, new ClientPacket(witch.getId(), false));

            event.getPlayer().getItemInHand(Hand.MAIN_HAND).hurtAndBreak(1, event.getPlayer(), (exp) -> {
                exp.broadcastBreakEvent(event.getHand());
            });
            witch.spawnAtLocation(ModItems.ITEM_WITCH_NOSE);


            event.setCanceled(true);
        }
}

private static boolean isHoldingShears(PlayerEntity player){
        return player.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ShearsItem;

}


    // If a player entity right-clicks a villager entity with a nose while holding shears, remove the villager's nose
    // Drop the nose as an item, damage the shears, and set a timer so the nose regrows after a set time
    @SubscribeEvent
    public static void shearNoseEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.getPlayer() instanceof ServerPlayerEntity && event.getHand() == Hand.MAIN_HAND) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            if (event.getTarget() instanceof VillagerEntity) {
                VillagerEntity villager = (VillagerEntity) event.getTarget();
                if (event.getTarget().getCapability(NOSE_CAP).isPresent()) {
                    INose capability = villager.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
                    if (capability.hasNose() && player.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ShearsItem) {
                        capability.setHasNose(false);
                        event.getTarget().playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                        ITimer timerCap = villager.getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
                        timerCap.setTimer(regrowthTime);
                        PacketDistributor.PacketTarget dest = PacketDistributor.TRACKING_ENTITY.with(event::getTarget);
                        PacketHandler.INSTANCE.send(dest, new ClientPacket(villager.getId(), false));


                        player.getItemInHand(Hand.MAIN_HAND).hurtAndBreak(1, player, (exp) -> {
                            exp.broadcastBreakEvent(event.getHand());
                        });
                        villager.spawnAtLocation(ModItems.ITEM_NOSE);

                        event.setCanceled(true);
                        return;
                    }
                    if (!capability.hasNose()) {
                        if (event.getItemStack().getItem() != Items.VILLAGER_SPAWN_EGG && villager.isAlive() && !villager.isSleeping() && !player.isCrouching() && !villager.isBaby()) {
                            player.awardStat(Stats.TALKED_TO_VILLAGER);
                            if (!villager.getOffers().isEmpty()) {
                                player.sendMessage(new TranslationTextComponent("translation.villagersnose.trade_refusal"), Util.NIL_UUID);
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
