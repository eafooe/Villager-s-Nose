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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;
import static com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider.TIMER_CAP;

public class EventHandlers {
    private static int ticksPerSecond = 20;
    private static int regrowthTime = Configuration.COMMON.regrowthTime.get() * ticksPerSecond;
    private static boolean noseRegenerates = Configuration.COMMON.noseRegenerates.get();

    // Add a nose and timer capability to villager entities
    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof VillagerEntity) {
            event.addCapability(NoseProvider.IDENTIFIER, new NoseProvider());
            event.addCapability(TimerProvider.IDENTIFIER, new TimerProvider());
        }
    }

    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinWorldEvent event){
        if (event.getEntity() instanceof VillagerEntity){
            ITimer timerCap = event.getEntity().getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
            if (timerCap.getTimer() > regrowthTime){
                timerCap.setTimer(regrowthTime);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof VillagerEntity && noseRegenerates) {
            INose noseCap = event.getEntityLiving().getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
            if (!noseCap.hasNose()) {
                ITimer timerCap = event.getEntityLiving().getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
                if (timerCap.getTimer() > 0) {
                    timerCap.decrementTimer();
                    VillagersNose.LOGGER.info("Time left: " + timerCap.getTimer());
                } else {
                    noseCap.setHasNose(true);
                    PacketDistributor.PacketTarget dest = PacketDistributor.TRACKING_ENTITY.with(event::getEntityLiving);
                    int entityId = event.getEntityLiving().getEntityId();
                    PacketHandler.INSTANCE.send(dest, new ClientPacket(entityId, true));
                }
            }
        }
    }

    // When a new client starts viewing the entity, notify it of existing data
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        PlayerEntity player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if (player instanceof ServerPlayerEntity && target instanceof VillagerEntity) {
            PacketDistributor.PacketTarget dest = PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player);
            boolean hasNose = target.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new).hasNose();
            int entityId = target.getEntityId();
            PacketHandler.INSTANCE.send(dest, new ClientPacket(entityId, hasNose));
        }
    }

    // If a player entity right-clicks a villager entity with a nose while holding shears, remove the villager's nose
    // Drop the nose as an item, damage the shears, and set a timer so the nose regrows after a set time
    @SubscribeEvent
    public static void shearNoseEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntityPlayer() instanceof ServerPlayerEntity && event.getHand() == Hand.MAIN_HAND) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityPlayer();
            if (event.getTarget() instanceof VillagerEntity) {
                VillagerEntity villager = (VillagerEntity) event.getTarget();
                if (event.getTarget().getCapability(NOSE_CAP).isPresent()) {
                    INose capability = villager.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
                    if (capability.hasNose() && player.getHeldItemMainhand().getItem() instanceof ShearsItem) {
                        capability.setHasNose(false);
                        ITimer timerCap = villager.getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
                        timerCap.setTimer(regrowthTime);
                        VillagersNose.LOGGER.info("Set regrowthTime to " + regrowthTime + " ticks");
                        PacketDistributor.PacketTarget dest = PacketDistributor.TRACKING_ENTITY.with(event::getTarget);
                        PacketHandler.INSTANCE.send(dest, new ClientPacket(villager.getEntityId(), false));
                        player.getHeldItemMainhand().damageItem(1, player, (exp) -> exp.sendBreakAnimation(event.getHand()));
                        villager.entityDropItem(ModItems.ITEM_NOSE);
                        event.setCanceled(true);
                        return;
                    }
                    if (!capability.hasNose()) {
                        if (event.getItemStack().getItem() != Items.VILLAGER_SPAWN_EGG && villager.isAlive() && !villager.isSleeping() && !player.isSneaking() && !villager.isChild()) {
                            player.addStat(Stats.TALKED_TO_VILLAGER);
                            if (!villager.getOffers().isEmpty()) {
                                player.sendMessage(new TranslationTextComponent("translation.villagersnose.trade_refusal", (Object) null));
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
