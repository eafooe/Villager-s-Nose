package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.capabilities.Nose.INose;
import com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider;
import com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider;
import com.emilyfooe.villagersnose.init.ModItems;
import com.emilyfooe.villagersnose.network.ServerPacket;
import com.emilyfooe.villagersnose.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;
import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;
import static com.emilyfooe.villagersnose.capabilities.Timer.TimerProvider.TIMER_CAP_KEY;

public class EventHandlers {
    private static int ticksPerSecond = 20;
    private static int regrowthTime = Configuration.COMMON.regrowthTime.get() * ticksPerSecond;

    // If a villager entity does not have a nose, decrement the nose regrowth timer until it hits zero.
    // When the timer hits zero, regrow the villager's nose.
   /* @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event){
        if (event.getEntityLiving() instanceof VillagerEntity){
            VillagerEntity villager = (VillagerEntity) event.getEntityLiving();
            INose noseCapability = villager.getCapability(NOSE_CAP, null).orElseThrow(() -> new RuntimeException("No inventory!"));
            ITimer timerCap = villager.getCapability(TIMER_CAP).orElseThrow(() -> new RuntimeException("No timer!"));
            if (!noseCapability.hasNose()){
                if (timerCap.getTimer() > 0){
                    timerCap.decrementTimer();
                } else {
                    noseCapability.setHasNose(true);
                }
            }
        }
    }*/

    // If a player entity right-clicks a villager entity with a nose while holding shears, remove the villager's nose
    // Drop the nose as an item, damage the shears, and set a timer so the nose regrows after a set time
    @SubscribeEvent
    public static void shearNoseEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof VillagerEntity && event.getTarget().getCapability(NOSE_CAP, null).isPresent() && event.getHand() == Hand.MAIN_HAND) {
            VillagerEntity villager = (VillagerEntity) event.getTarget();
            INose noseCapability = villager.getCapability(NOSE_CAP, null).orElseThrow(() -> new RuntimeException("No inventory!"));

            if (noseCapability.hasNose() && event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ShearsItem){
                VillagersNose.LOGGER.info("Player is holding Shears; Villager has Nose");

                if (event.getEntityPlayer() instanceof ServerPlayerEntity){
                    PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntityPlayer), new ServerPacket(villager.getEntityId(), false));
                }

                //noseCapability.setHasNose(false);
                ItemStack shears = event.getEntityPlayer().getHeldItemMainhand();
                if (!event.getWorld().isRemote){
                    shears.damageItem(1, event.getEntityPlayer(), (exp) -> exp.sendBreakAnimation(event.getHand()));
                }
                event.getEntity().entityDropItem(ModItems.ITEM_NOSE);
                event.setCanceled(true);
            } else if ((event.getItemStack().getItem() != Items.VILLAGER_SPAWN_EGG && villager.isAlive() && !villager.isSleeping() && !event.getEntityPlayer().isSneaking() && !villager.isChild())){
                event.getEntityPlayer().addStat(Stats.TALKED_TO_VILLAGER);
                if (!villager.getOffers().isEmpty() && !event.getWorld().isRemote) {
                    event.getEntityPlayer().sendMessage(new TranslationTextComponent("translation.villagersnose.trade_refusal", (Object) null));
                    event.setCanceled(true);
                }
            }
        }
    }

    public static final ResourceLocation ID_CAPABILITY_NOSE = new ResourceLocation(MODID, "nose_capability");
    // Add a nose and timer capability to villager entities
    @SubscribeEvent
    public static void addNoseBoolean(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof VillagerEntity){
            event.addCapability(ID_CAPABILITY_NOSE, new NoseProvider());
            event.addCapability(TIMER_CAP_KEY, new TimerProvider());
        }
    }
}
