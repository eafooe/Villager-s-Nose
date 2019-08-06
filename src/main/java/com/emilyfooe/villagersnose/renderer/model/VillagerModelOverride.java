package com.emilyfooe.villagersnose.renderer.model;

import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.capabilities.INose;
import com.emilyfooe.villagersnose.capabilities.Nose;
import com.emilyfooe.villagersnose.capabilities.NoseProvider;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import static com.emilyfooe.villagersnose.capabilities.NoseProvider.MY_CAPABILITY;

@OnlyIn(Dist.CLIENT)
public class VillagerModelOverride<T extends Entity> extends EntityModel<T> implements IHasHead, IHeadToggle {
    protected final RendererModel villagerHead;
    protected RendererModel villagerHat;
    protected final RendererModel villagerHatAccessory;
    protected final RendererModel villagerBody;
    protected final RendererModel villagerBodyAccessory;
    protected final RendererModel villagerArms;
    protected final RendererModel rightVillagerLeg;
    protected final RendererModel leftVillagerLeg;
    protected final RendererModel villagerNose;

    public VillagerModelOverride(){
        this(0.0F);
    }
    public VillagerModelOverride(float scale) {
        this(scale, 64, 64);
    }

    public VillagerModelOverride(float p_i51059_1_, int p_i51059_2_, int p_i51059_3_) {
        float f = 0.5F;
        this.villagerHead = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.villagerHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i51059_1_);
        this.villagerHat = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.villagerHat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.villagerHat.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i51059_1_ + 0.5F);
        this.villagerHead.addChild(this.villagerHat);
        this.villagerHatAccessory = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.villagerHatAccessory.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.villagerHatAccessory.setTextureOffset(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16, 16, 1, p_i51059_1_);
        this.villagerHatAccessory.rotateAngleX = (-(float) Math.PI / 2F);
        this.villagerHat.addChild(this.villagerHatAccessory);
        this.villagerNose = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.villagerNose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i51059_1_);
        this.villagerHead.addChild(this.villagerNose);
        this.villagerBody = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.villagerBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i51059_1_);
        this.villagerBodyAccessory = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.villagerBodyAccessory.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.villagerBodyAccessory.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i51059_1_ + 0.5F);
        this.villagerBody.addChild(this.villagerBodyAccessory);
        this.villagerArms = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.villagerArms.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i51059_1_);
        this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i51059_1_, true);
        this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i51059_1_);
        this.rightVillagerLeg = (new RendererModel(this, 0, 22)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51059_1_);
        this.leftVillagerLeg = (new RendererModel(this, 0, 22)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51059_1_);
    }

    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);



        if (entityIn.getCapability(MY_CAPABILITY).isPresent()){
                Nose nose = (Nose) entityIn.getCapability(MY_CAPABILITY).orElseThrow(() -> new RuntimeException("No inventory!"));;
                // Add a nose to a villager w/o a nose
                if (nose.getHasNose() && !villagerHead.childModels.contains(villagerNose)){
                    VillagersNose.LOGGER.info("Adding nose to a nose-blind villager...");
                    villagerHead.addChild(villagerNose);
                    // Remove a nose from a villager w/ a nose
                } else if (!nose.getHasNose() && villagerHead.childModels.contains(villagerNose)){
                    VillagersNose.LOGGER.info("Removing nose from a naughty villager...");
                    villagerHead.removeChild(villagerNose);
                }
            } else {
                VillagersNose.LOGGER.info("Could not find nose capability");
            }


        //VillagersNose.LOGGER.info("Rendering villager head...");
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

        this.villagerHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.villagerHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        if (flag) {
            this.villagerHead.rotateAngleZ = 0.3F * MathHelper.sin(0.45F * ageInTicks);
            this.villagerHead.rotateAngleX = 0.4F;
        } else {
            this.villagerHead.rotateAngleZ = 0.0F;
        }

        this.villagerArms.rotationPointY = 3.0F;
        this.villagerArms.rotationPointZ = -1.0F;
        this.villagerArms.rotateAngleX = -0.75F;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.rightVillagerLeg.rotateAngleY = 0.0F;
        this.leftVillagerLeg.rotateAngleY = 0.0F;
    }


    public RendererModel func_205072_a() {
        return this.villagerHead;
    }

    public void func_217146_a(boolean p_217146_1_) {
        villagerHead.showModel = p_217146_1_;
        villagerHat.showModel = p_217146_1_;
        villagerHatAccessory.showModel = p_217146_1_;
    }
}