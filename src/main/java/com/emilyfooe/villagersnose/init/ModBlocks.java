package com.emilyfooe.villagersnose.init;

import com.emilyfooe.villagersnose.block.BlockVillagerPlant;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

public class ModBlocks {
    public static List<Block> blocks = new LinkedList<>();
    public static Block BLOCK_VILLAGER_PLANT = createBlock("block_villager_plant");

    private static Block createBlock(String name) {
        Block block = new BlockVillagerPlant().setRegistryName(new ResourceLocation(MODID, name));
        blocks.add(block);
        return block;
    }
}
