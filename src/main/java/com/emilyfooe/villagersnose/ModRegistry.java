package com.emilyfooe.villagersnose;

import net.minecraft.item.Item;
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

}
