package com.emilyfooe.villagersnose.block;

import com.emilyfooe.villagersnose.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockVillagerCrop extends CropsBlock {

    public BlockVillagerCrop() {
        super(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.CROP).hardnessAndResistance(0));
    }

    // A nose item is used to plant this crop
    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("NullableProblems")
    protected IItemProvider getSeedsItem() {
        return ModItems.ITEM_NOSE;
    }

    // If the player destroys a full-grown crop, spawn a VillagerEntity. Otherwise, drop a nose item
    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        if (isMaxAge(state)) {
            spawnVillager((World) worldIn, pos);
        } else {
            ItemStack stack = new ItemStack(ModItems.ITEM_NOSE, 1);
            worldIn.addEntity(new ItemEntity((World) worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
    }

    // Spawns a villager at the specified block position
    private static void spawnVillager(World world, BlockPos pos) {
        VillagerEntity villagerEntity = EntityType.VILLAGER.create(world);
        if (villagerEntity != null) {
            villagerEntity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            addVillagerSpawnEffects(world, villagerEntity.posX, villagerEntity.posY, villagerEntity.posZ);
            world.addEntity(villagerEntity);
        }
    }

    // Creates a particle effect for villager spawns
    private static void addVillagerSpawnEffects(World world, double posX, double posY, double posZ) {
        world.addParticle(ParticleTypes.EXPLOSION, posX, posY, posZ, 0, 0, 0);
        world.playSound(posX, posY, posZ, SoundEvents.ENTITY_VILLAGER_CELEBRATE, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
    }
}
