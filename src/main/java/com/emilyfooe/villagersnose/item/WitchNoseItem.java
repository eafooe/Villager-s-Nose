package com.emilyfooe.villagersnose.item;

import com.emilyfooe.villagersnose.client.model.WitchNoseModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WitchNoseItem extends BaseNoseItem {
    public WitchNoseItem() {
        super(ArmorMaterial.DIAMOND);
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultModel) {
        WitchNoseModel.INSTANCE.rotate(entityLiving);
        return (A) WitchNoseModel.INSTANCE;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "villagersnose:textures/models/armor/witch_nose.png";
    }

}

/**
 *  private final ModelRenderer mole = (new ModelRenderer(this)).setTexSize(64, 128);
 *
 *    public WitchModel(float p_i46361_1_) {
 *       super(p_i46361_1_, 64, 128);
 *       this.mole.setPos(0.0F, -2.0F, 0.0F);
 *       this.mole.texOffs(0, 0).addBox(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, -0.25F);
 */