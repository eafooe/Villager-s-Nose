package com.emilyfooe.villagersnose;

import com.emilyfooe.villagersnose.item.ItemNose;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

public class ModItems {
    public static List<Item> items = new LinkedList<>();
    public static Item NOSE = new ItemNose().setRegistryName(new ResourceLocation(MODID, "villagers_nose"));

    public static ItemNose registerItemNose(String name){
        ItemNose nose = (ItemNose) new ItemNose().setRegistryName(new ResourceLocation(MODID, name));
        items.add(nose);
        return nose;
    }
}
