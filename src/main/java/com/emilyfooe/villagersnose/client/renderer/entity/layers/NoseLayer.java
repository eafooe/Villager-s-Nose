package com.emilyfooe.villagersnose.client.renderer.entity.layers;

import com.emilyfooe.villagersnose.init.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

@OnlyIn(Dist.CLIENT)
public class NoseLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation NOSE_LOCATION = new ResourceLocation(MODID, "textures/entity/nose.png");
    private final NoseModel<T> noseModel = new NoseModel<>();

    public NoseLayer(IEntityRenderer<T, M> renderer){
        super(renderer);
    }
    @Override
    public void render(MatrixStack ms, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T entityToRender, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        ItemStack stack = entityToRender.getItemBySlot(EquipmentSlotType.HEAD);
        if (shouldRender(stack, entityToRender)) {
           ms.pushPose();
           ms.translate(0.0D, 0.0D, 0.125D);
           this.getParentModel().copyPropertiesTo(this.noseModel);
           this.noseModel.setupAnim(entityToRender, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
            IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_225628_2_, RenderType.armorCutoutNoCull(getNoseTexture(stack, entityToRender)), false, stack.hasFoil());
           this.noseModel.renderToBuffer(ms, ivertexbuilder, p_225628_3_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
          ms.popPose();
        }

    }

    public boolean shouldRender(ItemStack stack,T entity){
        return stack.getItem() == ModItems.ITEM_NOSE;
    }

    public ResourceLocation getNoseTexture(ItemStack stack, T entity){
        return NOSE_LOCATION;
    }

}

