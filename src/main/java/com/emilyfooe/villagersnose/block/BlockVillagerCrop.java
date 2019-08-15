package com.emilyfooe.villagersnose.block;

import com.emilyfooe.villagersnose.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockVillagerCrop extends CropsBlock
{
    public BlockVillagerCrop() {
        super(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.CROP).hardnessAndResistance(0, 0));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected IItemProvider getSeedsItem() {
        return ModItems.ITEM_NOSE;
    }

}
