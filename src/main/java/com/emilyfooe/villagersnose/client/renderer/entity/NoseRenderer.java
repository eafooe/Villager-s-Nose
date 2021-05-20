package com.emilyfooe.villagersnose.client.renderer.entity;

import com.emilyfooe.villagersnose.client.renderer.entity.model.ModelNose;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NoseRenderer extends LivingRenderer<PlayerEntity, ModelNose<LivingEntity>> {
    private static final ResourceLocation NOSE_LOCATION =  new ResourceLocation("villagersnose:textures/models/armor/item_nose.png");

    public NoseRenderer(EntityRendererManager erm) {
        super(erm, new ModelNose<>(), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(PlayerEntity p_110775_1_) {
        return NOSE_LOCATION;
    }



}
