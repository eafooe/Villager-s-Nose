package com.emilyfooe.villagersnose.item;

import com.emilyfooe.villagersnose.Configuration;
import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.client.model.ModelNose;
import com.emilyfooe.villagersnose.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.common.extensions.IForgeItem;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ItemNose extends ArmorItem implements IForgeItem, IPlantable {
    private static final List<SoundEvent> villagerSounds = createVillagerSoundsList();

    public ItemNose() {
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(64).rarity(Rarity.COMMON).group(ItemGroup.COMBAT));
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
        if (playerEntity.ticksExisted % 20 == 0) {
            int posX = (int) Math.floor(playerEntity.posX);
            int posY = (int) Math.floor(playerEntity.posY);
            int posZ = (int) Math.floor(playerEntity.posZ);
            if (emeraldsAreNearby(world, posX, posY, posZ)) {
                Random rand = new Random();
                playerEntity.world.playSound(playerEntity, playerEntity.getPosition(), getRandomVillagerSound(), SoundCategory.AMBIENT, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultModel) {
        return (A) new ModelNose();
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "villagersnose:textures/models/armor/item_nose.png";
    }

    // List of villager sounds to play while near emeralds
    private static List<SoundEvent> createVillagerSoundsList() {
        List<SoundEvent> list = new LinkedList<>();
        list.add(SoundEvents.ENTITY_VILLAGER_NO);
        list.add(SoundEvents.ENTITY_VILLAGER_YES);
        list.add(SoundEvents.ENTITY_VILLAGER_TRADE);
        list.add(SoundEvents.ENTITY_VILLAGER_AMBIENT);
        return list;
    }

    // Picks a random villager sound to play
    private static SoundEvent getRandomVillagerSound() {
        return villagerSounds.get(new Random().nextInt(villagerSounds.size()));
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return ModBlocks.VILLAGER_CROP.getDefaultState();
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Crop;
    }

    // If the player right-clicks with the block on Farmland, plant a villager crop
    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        BlockState state = context.getWorld().getBlockState(context.getPos());
        Direction direction = context.getFace();
        FaceDirection faceDirection = FaceDirection.getFacing(direction);
        boolean playerCanInteractWithBlock = context.getPlayer().canPlayerEdit(context.getPos(), direction, stack);
        boolean blockCanSustainPlant = state.canSustainPlant(context.getWorld(), context.getPos(), direction, this);
        boolean blockOnTopIsAir = context.getWorld().getBlockState(context.getPos().add(0, 1, 0)).isAir(context.getWorld(), context.getPos());
        if (faceDirection == FaceDirection.UP && playerCanInteractWithBlock && blockCanSustainPlant && blockOnTopIsAir) {
            context.getWorld().setBlockState(context.getPos().add(0, 1, 0), ModBlocks.VILLAGER_CROP.getDefaultState());
            if (!context.getPlayer().isCreative()){
                stack.shrink(1);
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }


}
