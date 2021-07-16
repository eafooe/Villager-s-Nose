package com.emilyfooe.villagersnose.item;

import com.emilyfooe.villagersnose.Configuration;
import com.emilyfooe.villagersnose.client.model.NoseModel;
import com.emilyfooe.villagersnose.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.extensions.IForgeItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class VillagerNoseItem extends BaseNoseItem implements IForgeItem, IPlantable {
    private static final List<SoundEvent> villagerSounds = createVillagerSoundsList();

    public VillagerNoseItem() {
        super(ArmorMaterial.LEATHER);
    }

    public static boolean emeraldsAreNearby(World world, double posX, double posY, double posZ) {
        return emeraldsAreNearby(world, (int) posX, (int) posY, (int) posZ);
    }

    // Determines whether emerald ore is within range of the player
    public static boolean emeraldsAreNearby(World world, int posX, int posY, int posZ) {
        int range = Configuration.COMMON.searchRange.get();
        int minX = posX - range;
        int maxX = posX + range;
        int minZ = posZ - range;
        int maxZ = posZ + range;
        int minY = posY - range;
        int maxY = posY + range;

        for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int x = minX; x <= maxX; x++) {
                    Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == Blocks.EMERALD_BLOCK || block == Blocks.EMERALD_ORE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Checks if emerald ore is nearby every tick. If so, play random villager sounds
    @Override
    public void onArmorTick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (playerEntity.tickCount % 20 == 0) {
            int posX = (int) Math.floor(playerEntity.getX());
            int posY = (int) Math.floor(playerEntity.getY());
            int posZ = (int) Math.floor(playerEntity.getZ());
            if (emeraldsAreNearby(world, posX, posY, posZ)) {
                Random rand = new Random();
                playerEntity.playSound(getRandomVillagerSound(), 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultModel) {
        NoseModel.INSTANCE.rotate(entityLiving, itemStack);
        return (A) NoseModel.INSTANCE;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "villagersnose:textures/models/armor/nose.png";
    }

    // List of villager sounds to play while near emeralds
    private static List<SoundEvent> createVillagerSoundsList() {
        List<SoundEvent> list = new LinkedList<>();
        list.add(SoundEvents.VILLAGER_NO);
        list.add(SoundEvents.VILLAGER_YES);
        list.add(SoundEvents.VILLAGER_TRADE);
        list.add(SoundEvents.VILLAGER_AMBIENT);
        return list;
    }

    // Picks a random villager sound to play
    private static SoundEvent getRandomVillagerSound() {
        return villagerSounds.get(new Random().nextInt(villagerSounds.size()));
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return ModBlocks.VILLAGER_CROP.defaultBlockState();
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.CROP;
    }

    // If the player right-clicks with the block on Farmland, plant a villager crop
    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        Direction direction = context.getClickedFace();
        FaceDirection faceDirection = FaceDirection.fromFacing(direction);
        if (context.getPlayer() == null){
            return ActionResultType.FAIL;
        }
        boolean playerCanInteractWithBlock = context.getPlayer().mayUseItemAt(context.getClickedPos(), direction, stack);

        boolean blockCanSustainPlant = state.canSustainPlant(context.getLevel(), context.getClickedPos(), direction, this);
        boolean blockOnTopIsAir = context.getLevel().getBlockState(context.getClickedPos().above()).isAir(context.getLevel(), context.getClickedPos());
        if (faceDirection == FaceDirection.UP && playerCanInteractWithBlock && blockCanSustainPlant && blockOnTopIsAir) {
            context.getLevel().setBlockAndUpdate(context.getClickedPos().above(), ModBlocks.VILLAGER_CROP.defaultBlockState());
            if (!context.getPlayer().isCreative()){
                stack.shrink(1);
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }



}
