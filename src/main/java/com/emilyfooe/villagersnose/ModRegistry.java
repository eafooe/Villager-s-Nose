package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.init.ModBlocks;
import com.emilyfooe.villagersnose.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

    // Register all of the blocks in ModBlocks
    @SubscribeEvent
    public static void blockRegistry(RegistryEvent.Register<Block> event){
        for (Block block : ModBlocks.blocks){
            event.getRegistry().register(block);
        }
    }

    // Register all of the items in ModItems
    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        for (Item item : ModItems.items) {
            event.getRegistry().register(item);
        }
    }
}
