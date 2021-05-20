package com.emilyfooe.villagersnose.client.overrides;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Pretty much a copy-paste job
@OnlyIn(Dist.CLIENT)
public class OverrideVillagerHeldItemLayer<T extends LivingEntity> extends LayerRenderer<T, OverrideVillagerModel<T>> {
    private final ItemRenderer field_215347_a = Minecraft.getInstance().getItemRenderer();

    OverrideVillagerHeldItemLayer(IEntityRenderer<T, OverrideVillagerModel<T>> p_i50917_1_) {
        super(p_i50917_1_);
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {

    }

//    @Override
//    public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
//        ItemStack itemstack = entityIn.getItemBySlot(EquipmentSlotType.MAINHAND)
//        if (!itemstack.isEmpty()) {
//            Item item = itemstack.getItem();
//            Block block = Block.byItem(item);
//
//            GlStateManager.pushMatrix();
//
//            boolean flag = this.field_215347_a.shouldRenderItemIn3D(itemstack) && block.getRenderLayer() == BlockRenderLayer.TRANSLUCENT;
//            if (flag) {
//                GlStateManager.depthMask(false);
//            }
//
//            GlStateManager.translatef(0.0F, 0.4F, -0.4F);
//            GlStateManager.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
//            //TODO: refactor; ItemCameraTransforms is deprecated
//            this.field_215347_a.renderItem(itemstack, entityIn, ItemCameraTransforms.TransformType.GROUND, false);
//            if (flag) {
//                GlStateManager.depthMask(true);
//            }
//
//            GlStateManager.popMatrix();
//        }
//    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
