package com.emilyfooe.villagersnose.renderer.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class ModelVillagersNose extends BipedModel {
    public RendererModel modelNose;
    private boolean isArmor;

    public ModelVillagersNose(boolean isArmor) {
        this(0.0F, 0.0F, 8, 6);
        this.isArmor = isArmor;
    }

    public ModelVillagersNose(float scaleFactor, float rotationY, int textureWidth, int textureHeight) {
        super(scaleFactor, rotationY, 0, 0);
        this.modelNose = new RendererModel(this, 0, 0);

        this.modelNose.setTextureSize(textureWidth, textureHeight);
        this.modelNose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scaleFactor);
        this.modelNose.setRotationPoint(0.0F, rotationY - 2.0F, 0.0F);
        //this.field_78116_c.func_78792_a(this.modelNose);
        this.bipedHead.addChild(modelNose);
    }

    /*public void func_78088_a(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
        this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, entity);
        if (this.isArmor && entity instanceof PlayerEntity && ItemNose.searchEmeraldsNearby(entity.field_70170_p, (int)entity.field_70165_t, (int)entity.field_70163_u, (int)entity.field_70161_v)) {
            this.modelNose.field_82908_p = MathHelper.func_76134_b(p_78088_4_) * 0.03F;
        }

        this.field_78116_c.func_78785_a(p_78088_7_);
    }*/
}
