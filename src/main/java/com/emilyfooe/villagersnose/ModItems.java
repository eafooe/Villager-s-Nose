package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.item.ItemNose;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

class ModItems {
    static List<Item> items = new LinkedList<>();
    static Item NOSE = (ArmorItem) registerItemNose("nose");

    private static Item registerItemNose(String name){
        Item nose = new ItemNose().setRegistryName(new ResourceLocation(MODID, name));
        items.add(nose);
        return nose;
    }
}
