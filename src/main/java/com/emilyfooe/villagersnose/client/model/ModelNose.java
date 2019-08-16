package com.emilyfooe.villagersnose.client.model;

import com.emilyfooe.villagersnose.item.ItemNose;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelNose extends BipedModel<PlayerEntity> {
    private final RendererModel bipedNose;

    public ModelNose() {
        this(0.0F, 0.0F, 8, 6);
    }

    private ModelNose(float scaleFactor, float rotationY, int textureWidth, int textureHeight) {
        super(scaleFactor, rotationY, 0, 0);
        bipedNose = new RendererModel(this);
        bipedNose.setTextureSize(textureWidth, textureHeight); // image x, image y
        bipedNose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scaleFactor);
        bipedNose.setRotationPoint(0.0F, rotationY - 2.0F, 0.0F);
        bipedHead.addChild(bipedNose);
    }

    // If emerald ore is nearby, 'wiggle' the model by allowing its y-offset to bob up and down
    @Override
    public void setRotationAngles(PlayerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        // Literally the only time I've used trig outside of school
        if (ItemNose.emeraldsAreNearby(entity.world, (int) entity.posX, (int) entity.posY, (int) entity.posZ)) {
            bipedNose.offsetY = MathHelper.cos((ageInTicks % 10) * (0.2F) * ((float) Math.PI)) * 0.03125F;
        }
    }

}
