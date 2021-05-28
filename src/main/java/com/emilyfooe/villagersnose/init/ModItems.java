package com.emilyfooe.villagersnose.init;

import com.emilyfooe.villagersnose.item.ItemNose;
import com.emilyfooe.villagersnose.item.ItemWitchNose;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

// Initializes all items and adds them to a list
public class ModItems {
    public static List<Item> items = new LinkedList<>();
    public static Item ITEM_NOSE = addItem(new ItemNose(),"nose");
    public static Item ITEM_WITCH_NOSE = addItem(new ItemWitchNose(),"witch_nose");

    private static Item addItem(Item item, String name){
        item.setRegistryName(new ResourceLocation(MODID, name));
        items.add(item);
        return item;
    }
}
