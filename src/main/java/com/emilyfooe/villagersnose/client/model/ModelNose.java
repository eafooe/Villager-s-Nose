package com.emilyfooe.villagersnose.client.model;

import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.item.ItemNose;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelNose extends BipedModel<PlayerEntity> {
    public static ModelNose noseModel;
    public final RendererModel bipedNose;

    public ModelNose() {
        this(0.0F, 0.0F, 8, 6);
    }

    private ModelNose(float scaleFactor, float rotationY, int textureWidth, int textureHeight) {
        super(scaleFactor, rotationY, 0, 0);
        VillagersNose.LOGGER.info("Executed ModelNose()");
        bipedNose = new RendererModel(this);
        bipedNose.setTextureSize(textureWidth, textureHeight); // image x, image y
        bipedNose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scaleFactor);
        bipedNose.setRotationPoint(0.0F,  rotationY - 2.0F, 0.0F);
        bipedHead.addChild(bipedNose);
        VillagersNose.LOGGER.info("Default offsetY: " + bipedNose.offsetY);
    }

    @Override
    public void setRotationAngles(PlayerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
        super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (ItemNose.emeraldsAreNearby(entity.world, (int) entity.posX, (int) entity.posY, (int) entity.posZ)){
            VillagersNose.LOGGER.info("Found emeralds; wiggling nose!!!");
            bipedNose.offsetY = MathHelper.cos((ageInTicks % 10) * (0.2F) * ((float) Math.PI)) * 0.03125F;
            VillagersNose.LOGGER.info("Nose y-offset: " + bipedNose.offsetY);
        }
    }

}
