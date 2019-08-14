package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.capabilities.Nose.INose;
import com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;

public class EventHandlers {
    // private static int ticksPerSecond = 20;
    // static int regrowthTime = Configuration.COMMON.regrowthTime.get() * ticksPerSecond;

    // Add a nose and timer capability to villager entities
    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof VillagerEntity){
            event.addCapability(NoseProvider.IDENTIFIER, new NoseProvider());
        }
    }

    // When a new client starts viewing the entity, notify it of existing data
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event){
        PlayerEntity player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if (player instanceof ServerPlayerEntity && target instanceof VillagerEntity){
            PacketDistributor.PacketTarget dest = PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player);
            PacketHandler.INSTANCE.send(dest, new ClientPacket(target.getEntityId()));
        }
    }

    // Send to all players tracking entity
    @SubscribeEvent
    public static void entityJoinWorldEvent(EntityJoinWorldEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof VillagerEntity && !event.getWorld().isRemote){
            PacketDistributor.PacketTarget dest = PacketDistributor.TRACKING_ENTITY.with(() -> entity);
            //java.lang.ClassCastException: net.minecraft.client.multiplayer.ClientChunkProvider cannot be cast to net.minecraft.world.chunk.ServerChunkProvider
            PacketHandler.INSTANCE.send(dest, new ClientPacket(entity.getEntityId()));
        }
    }

    // If a player entity right-clicks a villager entity with a nose while holding shears, remove the villager's nose
    // Drop the nose as an item, damage the shears, and set a timer so the nose regrows after a set time
    @SubscribeEvent
    public static void shearNoseEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntityPlayer() instanceof ServerPlayerEntity && event.getHand() == Hand.MAIN_HAND){
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityPlayer();
            if (event.getTarget() instanceof VillagerEntity){
                VillagerEntity villager = (VillagerEntity) event.getTarget();
                if (event.getTarget().getCapability(NOSE_CAP).isPresent()){
                    INose capability = villager.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
                    if (capability.hasNose() && player.getHeldItemMainhand().getItem() instanceof ShearsItem){
                        capability.setHasNose(false);
                        PacketDistributor.PacketTarget dest = PacketDistributor.TRACKING_ENTITY.with(event::getTarget);
                        PacketHandler.INSTANCE.send(dest, new ClientPacket(villager.getEntityId()));
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
