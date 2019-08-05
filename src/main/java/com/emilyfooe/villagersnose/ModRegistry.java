package com.emilyfooe.villagersnose;


import com.emilyfooe.villagersnose.item.ItemNose;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {
    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(
                new ItemNose().setRegistryName(new ResourceLocation(MODID, "villagers_nose"))
        );
    }
}
