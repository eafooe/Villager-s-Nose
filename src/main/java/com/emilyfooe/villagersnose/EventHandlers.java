package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.block.BlockVillagerCrop;
import com.emilyfooe.villagersnose.capabilities.Nose.INose;
import com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider;
import com.emilyfooe.villagersnose.capabilities.Timer.ITimer;
import com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider;
import com.emilyfooe.villagersnose.client.renderer.entity.model.CustomIronGolemModel;
import com.emilyfooe.villagersnose.client.renderer.entity.model.CustomZombieVillagerModel;
import com.emilyfooe.villagersnose.init.ModItems;
import com.emilyfooe.villagersnose.network.ClientPacket;
import com.emilyfooe.villagersnose.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderLivingEvent;
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
    private static final int ticksPerSecond = 20;
    private static final int regrowthTime = Configuration.COMMON.regrowthTime.get() * ticksPerSecond;
    private static final boolean noseRegenerates = Configuration.COMMON.noseRegenerates.get();
    private static final List shearable = Arrays.asList(WitchEntity.class, VillagerEntity.class, WanderingTraderEntity.class);
    private static final List irregularShearable = Arrays.asList(ZombieVillagerEntity.class, EvokerEntity.class,
            PillagerEntity.class, VindicatorEntity.class, IllusionerEntity.class, IronGolemEntity.class);

    // Add a nose and timer capability to nose-shearable instances
    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (entityTypeIsShearable(event.getObject())) {
            event.addCapability(NoseProvider.IDENTIFIER, new NoseProvider());
            event.addCapability(TimerProvider.IDENTIFIER, new TimerProvider());
        }
    }

    private static boolean entityTypeIsShearable(Entity entity) {
        return shearable.contains(entity.getClass()) || irregularShearable.contains(entity.getClass());
    }

    private static boolean isHoldingShears(PlayerEntity player) {
        return player.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ShearsItem;
    }

    private static boolean isValidInteraction(Entity target, PlayerEntity player, ItemStack itemStack) {
        if (!(entityTypeIsShearable(target))) {
            return false;
        }
        if (itemStack.getItem() instanceof SpawnEggItem) {
            return false;
        }
        if (!target.isAlive()) {
            return false;
        }
        if (player.isCrouching()) {
            return false;
        }
        if (target instanceof AgeableEntity) {
            return !((AgeableEntity) target).isBaby();
        }
        return true;
    }


    // If the player reduces the nose regrowth time in the config file, update these changes
    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinWorldEvent event) {
        if (entityTypeIsShearable(event.getEntity())) {
            ITimer timerCap = event.getEntity().getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
            if (timerCap.getTimer() > regrowthTime) {
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


    private static void toggleNoseVisibility(Entity entity, EntityModel entityModel, boolean isVisible) {
        if (!(entityTypeIsShearable(entity))) {
            return;
        }
        if (entity instanceof ZombieVillagerEntity) {
            CustomZombieVillagerModel model = (CustomZombieVillagerModel) entityModel;
            model.nose.visible = isVisible;
        } else if (entity instanceof EvokerEntity || entity instanceof IllusionerEntity || entity instanceof PillagerEntity || entity instanceof VindicatorEntity) {
            IllagerModel model = (IllagerModel) entityModel;
            model.head.children.get(1).visible = isVisible;
        } else if (entity instanceof IronGolemEntity) {
            CustomIronGolemModel model = (CustomIronGolemModel) entityModel;
            model.nose.visible = isVisible;
        } else { // Witches, Villagers, and WanderingTrader entities use VillagerModel
            VillagerModel villagerModel = (VillagerModel) entityModel;
            villagerModel.nose.visible = isVisible;
        }

    }

    /***
     * This should only be executed on the logical client
     */
    @SubscribeEvent
    public static void handleNoseVisibility(RenderLivingEvent.Pre event) {
        if (!(entityTypeIsShearable(event.getEntity()))) {
            return;
        }
        Entity shearableEntity = event.getEntity();
        toggleNoseVisibility(shearableEntity, event.getRenderer().getModel(), hasNose(shearableEntity));
    }

    /***
     * This should only be executed on the logical server.
     *
     * If a nose-shearable entity does <i>not</i> have a nose and noses should regenerate (set in the mod config),
     * count down the time until the entity's nose should regrow. Once this time requirement has been hit,
     * regrow the entity's nose.
     *
     */
    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving().getCommandSenderWorld().isClientSide) {
            return;
        }
        if (!(entityTypeIsShearable(event.getEntityLiving())) || !noseRegenerates) {
            return;
        }
        LivingEntity target = event.getEntityLiving();
        if (target.getCapability(NOSE_CAP).isPresent() && target.getCapability(TIMER_CAP).isPresent()) {
            INose noseCap = target.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
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
    public static void rightClickToHarvest(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().isClientSide) {
            return;
        }

        BlockState blockState = event.getWorld().getBlockState(event.getPos());
        Block clickedBlock = event.getWorld().getBlockState(event.getPos()).getBlock();
        if (clickedBlock instanceof BlockVillagerCrop && ((BlockVillagerCrop) clickedBlock).isMaxAge(blockState)) {
            VillagersNose.LOGGER.info("Interacted w/ villager crop");
            BlockVillagerCrop villagerCrop = (BlockVillagerCrop) event.getWorld().getBlockState(event.getPos()).getBlock();
            villagerCrop.destroy(event.getWorld(), event.getPos(), event.getWorld().getBlockState(event.getPos()));
            event.getWorld().destroyBlock(event.getPos(), false);
        }
    }


    private static boolean hasNose(Entity entity) {
        if (!entity.getCapability(NOSE_CAP).isPresent()) {
            return false;
        }
        return entity.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new).hasNose();
    }

    private static void spawnNoseItem(Entity entity) {
        if (entity instanceof IronGolemEntity) {
            entity.spawnAtLocation(ModItems.ITEM_IRON_GOLEM_NOSE);
            return;
        }
        if (entity instanceof WitchEntity){
            entity.spawnAtLocation(ModItems.ITEM_WITCH_NOSE);
            return;
        }
        entity.spawnAtLocation(ModItems.ITEM_NOSE);
    }

    // If a player entity right-clicks a villager entity with a nose while holding shears, remove the villager's nose
    // Drop the nose as an item, damage the shears, and set a timer so the nose regrows after a set time
    @SubscribeEvent
    public static void shearNoseEvent(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getPlayer() instanceof ServerPlayerEntity)) {
            return;
        }
        if (!(entityTypeIsShearable(event.getTarget()))) {
            return;
        }

        if (isHoldingShears(event.getPlayer()) && hasNose(event.getTarget())) {
            Entity target = event.getTarget();
            if (target.getCapability(NOSE_CAP).isPresent() && target.getCapability(TIMER_CAP).isPresent()) {
                INose noseCap = target.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
                noseCap.setHasNose(false);
                event.getTarget().playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                ITimer timerCap = target.getCapability(TIMER_CAP).orElseThrow(NullPointerException::new);
                timerCap.setTimer(regrowthTime);
                PacketDistributor.PacketTarget dest = PacketDistributor.TRACKING_ENTITY.with(event::getTarget);
                PacketHandler.INSTANCE.send(dest, new ClientPacket(target.getId(), false));
                event.getPlayer().getItemInHand(Hand.MAIN_HAND).hurtAndBreak(1, event.getPlayer(), (exp) -> exp.broadcastBreakEvent(event.getHand()));
                spawnNoseItem(target);
                event.setCanceled(true);
                return;
            }
        }
        if (!hasNose(event.getTarget())) {
            if (isValidInteraction(event.getTarget(), event.getPlayer(), event.getItemStack())) {
                if (event.getTarget() instanceof VillagerEntity) {
                    VillagerEntity villager = (VillagerEntity) event.getTarget();
                    event.getPlayer().awardStat(Stats.TALKED_TO_VILLAGER);
                    if (!villager.getOffers().isEmpty()) {
                        event.getPlayer().sendMessage(new TranslationTextComponent("translation.villagersnose.trade_refusal"), Util.NIL_UUID);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
