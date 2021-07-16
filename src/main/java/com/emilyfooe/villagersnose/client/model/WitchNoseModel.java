package com.emilyfooe.villagersnose.client.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;


public class WitchNoseModel extends BaseNoseModel {
    ModelRenderer mole;
    public static final WitchNoseModel INSTANCE = new WitchNoseModel();

    private WitchNoseModel() {
        super(0, 0, 16, 16);
        mole = (new ModelRenderer(this)).setTexSize(16, 16);
        mole.texOffs(8, 0);
        mole.addBox(0.0F, -1.0F, -6.75F, 1, 1, 1, -0.25F);
        bipedNose.addChild(mole);
    }


    public void rotate(LivingEntity entityIn) {
        //this.bipedNose.setPos(0.0F, -2.0F, 0.0F);
        float f = 0.05F;
        this.bipedNose.xRot = MathHelper.sin((float)entityIn.tickCount * f) * 4.5F * ((float)Math.PI / 180F);
        this.bipedNose.yRot = 0.0F;
        this.bipedNose.zRot = MathHelper.cos((float)entityIn.tickCount * f) * 2.5F * ((float)Math.PI / 180F);
    }


//
//    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
//        modelRenderer.xRot = x;
//        modelRenderer.yRot = y;
//        modelRenderer.zRot = z;
//    }
}
