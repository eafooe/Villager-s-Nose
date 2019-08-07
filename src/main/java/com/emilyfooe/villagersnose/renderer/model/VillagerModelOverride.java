package com.emilyfooe.villagersnose.renderer.model;

import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.capabilities.Nose.Nose;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;

@OnlyIn(Dist.CLIENT)
public class VillagerModelOverride<T extends Entity> extends EntityModel<T> implements IHasHead, IHeadToggle {
    private final RendererModel villagerHead;
    private RendererModel villagerHat;
    private final RendererModel villagerHatAccessory;
    private final RendererModel villagerBody;
    private final RendererModel villagerBodyAccessory;
    private final RendererModel villagerArms;
    private final RendererModel rightVillagerLeg;
    private final RendererModel leftVillagerLeg;
    private final RendererModel villagerNose;

    public VillagerModelOverride(){
        this(0.0F);
    }
    VillagerModelOverride(float scale) {
        this(scale, 64, 64);
    }

    private VillagerModelOverride(float p_i51059_1_, int p_i51059_2_, int p_i51059_3_) {
        float f = 0.5F;
        villagerHead = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        villagerHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i51059_1_);
        villagerHat = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        villagerHat.setRotationPoint(0.0F, 0.0F, 0.0F);
        villagerHat.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i51059_1_ + 0.5F);
        villagerHead.addChild(villagerHat);
        villagerHatAccessory = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        villagerHatAccessory.setRotationPoint(0.0F, 0.0F, 0.0F);
        villagerHatAccessory.setTextureOffset(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16, 16, 1, p_i51059_1_);
        villagerHatAccessory.rotateAngleX = (-(float) Math.PI / 2F);
        villagerHat.addChild(villagerHatAccessory);
        villagerNose = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        villagerNose.setRotationPoint(0.0F, -2.0F, 0.0F);
        villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i51059_1_);
        villagerHead.addChild(villagerNose);
        villagerBody = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        villagerBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i51059_1_);
        villagerBodyAccessory = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        villagerBodyAccessory.setRotationPoint(0.0F, 0.0F, 0.0F);
        villagerBodyAccessory.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i51059_1_ + 0.5F);
        villagerBody.addChild(villagerBodyAccessory);
        villagerArms = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        villagerArms.setRotationPoint(0.0F, 2.0F, 0.0F);
        villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i51059_1_);
        villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i51059_1_, true);
        villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i51059_1_);
        rightVillagerLeg = (new RendererModel(this, 0, 22)).setTextureSize(p_i51059_2_, p_i51059_3_);
        rightVillagerLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51059_1_);
        leftVillagerLeg = (new RendererModel(this, 0, 22)).setTextureSize(p_i51059_2_, p_i51059_3_);
        leftVillagerLeg.mirror = true;
        leftVillagerLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51059_1_);
    }

    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if (entityIn.getCapability(NOSE_CAP).isPresent()) {
            VillagersNose.LOGGER.info("Found nose capability");
            Nose nose = (Nose) entityIn.getCapability(NOSE_CAP).orElseThrow(() -> new RuntimeException("No inventory!"));
            // Add a nose to a villager w/o a nose
            if (nose.getHasNose() && !villagerHead.childModels.contains(villagerNose)) {
                VillagersNose.LOGGER.info("Adding nose to a nose-blind villager...");
                villagerHead.addChild(villagerNose);
                // Remove a nose from a villager w/ a nose
            } else if (!nose.getHasNose() && villagerHead.childModels.contains(villagerNose)) {
                VillagersNose.LOGGER.info("Removing nose from a naughty villager...");
                villagerHead.removeChild(villagerNose);
            }
        } else {
            VillagersNose.LOGGER.info("Could not find nose capability");
        }

        villagerHead.render(scale);
        villagerBody.render(scale);
        rightVillagerLeg.render(scale);
        leftVillagerLeg.render(scale);
        villagerArms.render(scale);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean flag = false;
        if (entityIn instanceof AbstractVillagerEntity) {
            flag = ((AbstractVillagerEntity) entityIn).getShakeHeadTicks() > 0;
        }

        villagerHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        villagerHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        if (flag) {
            villagerHead.rotateAngleZ = 0.3F * MathHelper.sin(0.45F * ageInTicks);
            villagerHead.rotateAngleX = 0.4F;
        } else {
            villagerHead.rotateAngleZ = 0.0F;
        }

        villagerArms.rotationPointY = 3.0F;
        villagerArms.rotationPointZ = -1.0F;
        villagerArms.rotateAngleX = -0.75F;
        rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        rightVillagerLeg.rotateAngleY = 0.0F;
        leftVillagerLeg.rotateAngleY = 0.0F;
    }

    public RendererModel func_205072_a() {
        return villagerHead;
    }

    public void func_217146_a(boolean p_217146_1_) {
        villagerHead.showModel = p_217146_1_;
        villagerHat.showModel = p_217146_1_;
        villagerHatAccessory.showModel = p_217146_1_;
    }
}