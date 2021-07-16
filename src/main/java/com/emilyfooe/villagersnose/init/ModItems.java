package com.emilyfooe.villagersnose.init;

import com.emilyfooe.villagersnose.item.BaseNoseItem;
import com.emilyfooe.villagersnose.item.IronGolemNoseItem;
import com.emilyfooe.villagersnose.item.VillagerNoseItem;
import com.emilyfooe.villagersnose.item.WitchNoseItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

// Initializes all items and adds them to a list
public class ModItems {
    public static List<Item> items = new LinkedList<>();
    public static Item ITEM_NOSE = addItem(new VillagerNoseItem(),"nose");
    public static Item ITEM_WITCH_NOSE = addItem(new WitchNoseItem(),"witch_nose");
   // public static Item ITEM_IRON_GOLEM_NOSE = addItem(new IronGolemNoseItem(), "iron_golem_nose");
    public static Item ITEM_IRON_GOLEM_NOSE = addItem(new IronGolemNoseItem(), "iron_golem_nose");
    //public static Item BASE_NOSE_ITEM = addItem(new BaseNoseItem(), "base_nose");

    private static Item addItem(Item item, String name){
        item.setRegistryName(new ResourceLocation(MODID, name));
        items.add(item);
        return item;
    }


}
