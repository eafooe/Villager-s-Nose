package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.item.ItemNose;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

public class ModItems {
    static List<Item> items = new LinkedList<>();
    public static Item NOSE = (ArmorItem) registerItemNose("villagers_nose");

    private static Item registerItemNose(String name){
        Item nose = new ItemNose().setRegistryName(new ResourceLocation(MODID, name));
        items.add(nose);
        return nose;
    }
}
