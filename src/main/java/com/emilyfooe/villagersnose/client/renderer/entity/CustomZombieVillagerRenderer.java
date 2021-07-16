package com.emilyfooe.villagersnose.client.renderer.entity;

import com.emilyfooe.villagersnose.client.renderer.entity.model.CustomZombieVillagerModel;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomZombieVillagerRenderer extends BipedRenderer<ZombieVillagerEntity, CustomZombieVillagerModel<ZombieVillagerEntity>> {
    private static final ResourceLocation ZOMBIE_VILLAGER_LOCATION = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");

    public CustomZombieVillagerRenderer(EntityRendererManager p_i50952_1_, IReloadableResourceManager p_i50952_2_) {
        super(p_i50952_1_, new CustomZombieVillagerModel<>(0.0F, false), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new CustomZombieVillagerModel(0.5F, true), new CustomZombieVillagerModel(1.0F, true)));
        this.addLayer(new VillagerLevelPendantLayer<>(this, p_i50952_2_, "zombie_villager"));
    }

    public ResourceLocation getTextureLocation(ZombieVillagerEntity p_110775_1_) {
        return ZOMBIE_VILLAGER_LOCATION;
    }

    protected boolean isShaking(ZombieVillagerEntity p_230495_1_) {
        return p_230495_1_.isConverting();
    }
}
