package com.emilyfooe.villagersnose.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class NoseLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends BipedArmorLayer<T, M, A> {

    public NoseLayer(IEntityRenderer<T, M> entityRenderer, A modelLeggings, A modelArmor) {
        super(entityRenderer, modelLeggings, modelArmor);
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer renderer, int packedLightIn, T entity, float limbSwing, float limbSwingAmount, float partialTicks,
                       float ageInTicks, float netHeadYaw, float headPitch) {
        //renderArmorPart(matrix, renderer, entity, EquipmentSlotType.CHEST, packedLightIn, partialTicks);
        //renderArmorPart(matrix, renderer, entity, EquipmentSlotType.LEGS, packedLightIn, partialTicks);
        //renderArmorPart(matrix, renderer, entity, EquipmentSlotType.FEET, packedLightIn, partialTicks);
        renderArmorPart(matrix, renderer, entity, EquipmentSlotType.HEAD, packedLightIn, partialTicks);
    }

    private void renderArmorPart(MatrixStack matrix, IRenderTypeBuffer renderer, T entity, EquipmentSlotType slot, int light, float partialTicks) {
        ItemStack stack = entity.getItemBySlot(slot);
        Item item = stack.getItem();

//                CustomArmor model = ((ISpecialGear) item).getGearModel();
//                getParentModel().copyPropertiesTo((BipedModel<T>) model);
//                setPartVisibility((A) model, slot);
//                model.render(matrix, renderer, light, OverlayTexture.NO_OVERLAY, partialTicks, stack.hasFoil(), entity, stack);

    }
}