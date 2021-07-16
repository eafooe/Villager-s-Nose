package com.emilyfooe.villagersnose.client.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BaseNoseModel extends BipedModel<LivingEntity> {
    public static final BaseNoseModel INSTANCE = new BaseNoseModel();
    public final ModelRenderer bipedNose;

    public BaseNoseModel() {
        this(0.0F, 0.0F, 16, 16);
    }

    public BaseNoseModel(float scaleFactor, float rotationY, int textureWidth, int textureHeight) {
        super(scaleFactor, rotationY, 0, 0);
        bipedNose = new ModelRenderer(this);
        bipedNose.setTexSize(textureWidth, textureHeight); // image x, image y
        bipedNose.addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, 0);
        head.addChild(bipedNose);
    }
}
