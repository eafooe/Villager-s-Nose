package com.emilyfooe.villagersnose.init;

import com.emilyfooe.villagersnose.block.BlockVillagerCrop;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

// Initializes all blocks and adds them to a list
public class ModBlocks {
    public static List<Block> blocks = new LinkedList<>();
    public static CropsBlock VILLAGER_CROP = (CropsBlock) createVillagerCropBlock("block_villager_crop");

    private static Block createVillagerCropBlock(String name){
        Block block = new BlockVillagerCrop().setRegistryName(new ResourceLocation(MODID, name));
        blocks.add(block);
        return block;
    }
}
