package com.emilyfooe.villagersnose.item;

import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.renderer.model.ModelVillagersNose;
import javafx.geometry.Side;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;

public class ItemNose extends ArmorItem implements IPlantable {
    public ItemNose(){
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(64).rarity(Rarity.COMMON).group(ItemGroup.COMBAT));
    }

    @OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, int armorSlot) {
        VillagersNose.LOGGER.info("getArmorModel fired");
        return new ModelVillagersNose(true);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return "villagersnose:textures/models/nose_armor.png";
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return null;
    }
}
