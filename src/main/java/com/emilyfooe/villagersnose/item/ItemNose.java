package com.emilyfooe.villagersnose.item;

import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.renderer.model.ModelNose;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.extensions.IForgeItem;

import javax.annotation.Nullable;

public class ItemNose extends ArmorItem implements IForgeItem, IPlantable {
    public ItemNose(){
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(64).rarity(Rarity.COMMON).group(ItemGroup.COMBAT));
    }


    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultModel)
    {
        VillagersNose.LOGGER.info("getArmorModel fired");
        return (A) new ModelNose();
        // defaultModel.bipedHead = new ModelNose().bipedNose; // has nose, entire head is tan
        // defaultModel.bipedHeadwear = new ModelNose().bipedNose; // has nose, but tan area around head
        // defaultModel.bipedHeadwear.addChild(new ModelNose().bipedNose); (flat, no nose)
        // defaultModel.bipedHead.addChild(new ModelNose().bipedNose); (flat, no nose)

    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "villagersnose:textures/models/armor/nose.png";
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return null;
    }
}
