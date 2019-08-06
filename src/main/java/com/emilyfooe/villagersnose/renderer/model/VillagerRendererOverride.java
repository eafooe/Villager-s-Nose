package com.emilyfooe.villagersnose.renderer.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillagerRendererOverride extends MobRenderer<VillagerEntity, VillagerModelOverride<VillagerEntity>> {
    private static final ResourceLocation field_217779_a = new ResourceLocation("textures/entity/villager/villager.png");

    public VillagerRendererOverride(EntityRendererManager manager){
        super(manager, new VillagerModelOverride<>(0.0F), 0.5F);
        addLayer(new HeadLayer<>(this));
    }
    public VillagerRendererOverride(EntityRendererManager manager, IReloadableResourceManager p_i50954_2_) {
        super(manager, new VillagerModelOverride<>(0.0F), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new VillagerLevelPendantLayer<>(this, p_i50954_2_, "villager"));
        //addLayer(new VillagerHeldItemLayer<VillagerEntity>(this));
    }

    protected ResourceLocation getEntityTexture(VillagerEntity entity) {
        return field_217779_a;
    }

    protected void preRenderCallback(VillagerEntity entitylivingbaseIn, float partialTickTime) {
        float f = 0.9375F;
        if (entitylivingbaseIn.isChild()) {
            f = (float)((double)f * 0.5D);
            this.shadowSize = 0.25F;
        } else {
            this.shadowSize = 0.5F;
        }

        GlStateManager.scalef(f, f, f);
    }
}