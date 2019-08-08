package com.emilyfooe.villagersnose.init;

import com.emilyfooe.villagersnose.item.ItemNose;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

public class ModItems {
    public static List<Item> items = new LinkedList<>();
    public static Item ITEM_NOSE = (ArmorItem) createItem("item_nose");

    private static Item createItem(String name){
        Item nose = new ItemNose().setRegistryName(new ResourceLocation(MODID, name));
        items.add(nose);
        return nose;
    }
}
