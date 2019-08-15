package com.emilyfooe.villagersnose.block;

import com.emilyfooe.villagersnose.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockVillagerPlant extends CropsBlock
{
    public BlockVillagerPlant()
    {
        super(Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.CROP));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemProvider getSeedsItem(){
        return ModItems.ITEM_NOSE;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state){
        return new ItemStack(getSeedsItem());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result){
        if (!world.isRemote && isMaxAge(state)){
            VillagerEntity villager = new VillagerEntity(EntityType.VILLAGER, world);
            villager.setPosition(pos.getX(), pos.getY(), pos.getZ());
            world.setBlockState(pos, this.withAge(0));
            return true;
        }
        return false;
    }
}
