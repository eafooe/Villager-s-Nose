package com.emilyfooe.villagersnose.item;

import com.emilyfooe.villagersnose.Configuration;
import com.emilyfooe.villagersnose.Utilities;
import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.client.model.ModelNose;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.extensions.IForgeItem;

import javax.annotation.Nullable;
import java.util.Random;

public class ItemNose extends ArmorItem implements IForgeItem, IPlantable {
    public ItemNose(){
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(64).rarity(Rarity.COMMON).group(ItemGroup.COMBAT));
    }

    private static boolean emeraldsAreNearby(World world, int posX, int posY, int posZ){
        int range = Configuration.COMMON.searchRange.get();
        VillagersNose.LOGGER.info("Current position: {" + posX + ", " + posY + ", " + posZ + "}");
        VillagersNose.LOGGER.info("Search range (x): " + (posX - range) + " - " + (posX + range));
        VillagersNose.LOGGER.info("Search range (y): " + (posY - range) + " - " + (posY + range));
        VillagersNose.LOGGER.info("Search range (z): " + (posZ - range) + " - " + (posZ + range));
        // X-Coordinate Range: (posX - 5) to (posX + 5)
        int minX = posX - range;
        int maxX = posX + range;
        // Z-Coordinate Range: (posZ - 5) to (posZ + 5)
        int minZ = posZ - range;
        int maxZ = posZ + range;
        // Y-Coordinate Range: (posY - 5) to (posY + 5)
        int minY = posY - range;
        int maxY = posY + range;

        for (int y = minY; y <= maxY; y++){
            for (int z = minZ; z <= maxZ; z++){
                for (int x = minX; x <= maxX; x++){
                    Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == Blocks.EMERALD_BLOCK || block == Blocks.EMERALD_ORE){
                        if (world.getDayTime() % 40 == 0){
                            VillagersNose.LOGGER.info("Found emeralds!");
                        }
                        return true;
                    }
                }
            }
        }
        if (world.getDayTime() % 40 == 0){
            VillagersNose.LOGGER.info("Failed to find emeralds");
        }
        return false;
    }

    @Override
    public void onArmorTick(ItemStack itemStack, World world, PlayerEntity playerEntity){
        if (!world.isRemote){
            if (playerEntity.ticksExisted % 20 == 0){
                int posX = (int) Math.floor(playerEntity.posX);
                int posY = (int) Math.floor(playerEntity.posY);
                int posZ = (int) Math.floor(playerEntity.posZ);
                if (emeraldsAreNearby(world, posX, posY, posZ))
                {
                    VillagersNose.LOGGER.info("Playing random villager sounds...");
                    Random rand = new Random();
                    playerEntity.playSound(Utilities.getRandomVillagerSound(), 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultModel)
    {
        VillagersNose.LOGGER.info("getArmorModel fired");
        return (A) new ModelNose();
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "villagersnose:textures/models/armor/item_nose.png";
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return null;
    }
}
