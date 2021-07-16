package com.emilyfooe.villagersnose.client.model;

import com.emilyfooe.villagersnose.item.VillagerNoseItem;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class NoseModel extends BipedModel<LivingEntity>  {
    public static final NoseModel INSTANCE = new NoseModel();
    public final ModelRenderer bipedNose;

    public NoseModel() {
        this(0.0F, 0.0F, 8, 6);
    }


    public NoseModel(float scaleFactor, float rotationY, int textureWidth, int textureHeight) {
        super(scaleFactor, rotationY, 0, 0);
        bipedNose = new ModelRenderer(this);
        bipedNose.setTexSize(textureWidth, textureHeight); // image x, image y
        bipedNose.addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, 0);
        head.addChild(bipedNose);
    }

//    @Override
//    public void setupAnim(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//        if (VillagerNoseItem.emeraldsAreNearby(entityIn.level, entityIn.getX(), entityIn.getY(), entityIn.getZ())){
//            bipedNose.y = MathHelper.cos((entityIn.tickCount % 10) * (0.2F) * ((float) Math.PI)) * 0.03125F;
//        }
//    }

    public void rotate(LivingEntity entity, ItemStack itemStack) {
        if (entity instanceof AbstractClientPlayerEntity) {
            if (VillagerNoseItem.emeraldsAreNearby(entity.level, entity.getX(), entity.getY(), entity.getZ())){
                bipedNose.y = MathHelper.cos((entity.tickCount % 10) * (0.2F) * ((float) Math.PI)) * 0.3125F;
            }
        }
    }
//
//    public void render(@Nonnull MatrixStack matrix, @Nonnull IRenderTypeBuffer renderer, int light, int overlayLight, boolean hasEffect) {
//        renderToBuffer(matrix, ItemRenderer.getFoilBufferDirect(renderer, renderType(this.getTexture()), false, hasEffect), light, overlayLight, 1, 1, 1, 1);
//    }
//
//    @Override
//    public void renderToBuffer(@Nonnull MatrixStack matrix, @Nonnull IVertexBuilder vertexBuilder, int light, int overlayLight, float red, float green, float blue, float alpha) {
//        bipedNose.render(matrix, vertexBuilder, light, overlayLight, red, green, blue, alpha);
//    }

//    @Override
//    public ResourceLocation getTexture() {
//        return new ResourceLocation(VillagersNose.MODID, "textures/models/armor/nose.png");
//    }
}
