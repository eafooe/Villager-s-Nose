package com.emilyfooe.villagersnose.client.overrides;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

// Pretty much a copy-paste job
@OnlyIn(Dist.CLIENT)
public class OverrideVillagerRenderer extends MobRenderer<VillagerEntity, OverrideVillagerModel<VillagerEntity>> implements IRenderFactory<VillagerEntity> {


    @Override
    public EntityRenderer<? super VillagerEntity> createRenderFor(EntityRendererManager manager) {
        return new OverrideVillagerRenderer(manager, (IReloadableResourceManager) Minecraft.getInstance().getResourceManager());
    }

    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");

    public OverrideVillagerRenderer(EntityRendererManager rendererManager, IReloadableResourceManager resourceManager) {
        super(rendererManager, new OverrideVillagerModel<>(0.0F), 0.5F);
        addLayer(new HeadLayer<>(this));
        addLayer(new VillagerLevelPendantLayer<>(this, resourceManager, "villager"));
        addLayer(new OverrideVillagerHeldItemLayer<>(this));
    }

    protected void scale(VillagerEntity villagerEntity, MatrixStack stack, float p) {
        float f = 0.9375F;
        if (villagerEntity.isBaby()) {
            f = (float) ((double) f * 0.5D);
            this.shadowRadius = 0.25F;
        } else {
            this.shadowRadius = 0.5F;
        }

       stack.scale(f, f, f);
    }

    @Override
    public ResourceLocation getTextureLocation(VillagerEntity p_110775_1_) {
        return VILLAGER_BASE_SKIN;
    }


}
