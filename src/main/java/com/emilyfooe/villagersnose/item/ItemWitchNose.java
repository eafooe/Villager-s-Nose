package com.emilyfooe.villagersnose.item;

import com.emilyfooe.villagersnose.api.VillagersNoseAPI;
import com.emilyfooe.villagersnose.client.renderer.entity.model.ModelNose;
import com.emilyfooe.villagersnose.client.renderer.entity.model.ModelWitchNose;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemWitchNose extends ItemNose {
    public ItemWitchNose() {
        super(VillagersNoseAPI.FLESH_ARMOR_MATERIAL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultModel) {
        ModelWitchNose.INSTANCE.rotate(entityLiving, itemStack);
        return (A) ModelWitchNose.INSTANCE;
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