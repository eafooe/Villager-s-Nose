package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.init.ModBlocks;
import com.emilyfooe.villagersnose.init.ModItems;
import com.emilyfooe.villagersnose.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event){
        for(Item item : ModItems.items){
            event.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void blockRegistry(RegistryEvent.Register<Block> event){
        for(Block block : ModBlocks.blocks){
            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public static void soundRegistry(RegistryEvent.Register<SoundEvent> event){
        for (SoundEvent sound: ModSounds.sounds){
            event.getRegistry().register(sound);
        }
    }

}
