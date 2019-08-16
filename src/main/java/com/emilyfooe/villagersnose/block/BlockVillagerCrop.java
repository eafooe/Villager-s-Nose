package com.emilyfooe.villagersnose.block;

import com.emilyfooe.villagersnose.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.entity.EntityType.VILLAGER;

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

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        if (isMaxAge(state)){
            // Spawn villager
            VillagerEntity villager = VILLAGER.create((World) worldIn);
            assert villager != null;
            villager.setPosition(pos.getX(), pos.getY(), pos.getZ());
            worldIn.addEntity(villager);
        } else {
            // Return nose item
            worldIn.addEntity(new ItemEntity((World) worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.ITEM_NOSE, 1)));
        }
    }
}
