package com.emilyfooe.villagersnose.renderer.model;

import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.item.ItemNose;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
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
        bipedNose.setRotationPoint(0.0F, rotationY - 2.0F, 0.0F);
        bipedHead.addChild(bipedNose);
    }

    @Override
    public void render(PlayerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }


    /*public void func_78088_a(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
        this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, entity);
        if (this.isArmor && entity instanceof PlayerEntity && ItemNose.searchEmeraldsNearby(entity.field_70170_p, (int)entity.field_70165_t, (int)entity.field_70163_u, (int)entity.field_70161_v)) {
            this.modelNose.field_82908_p = MathHelper.func_76134_b(p_78088_4_) * 0.03F;
        }

        this.field_78116_c.func_78785_a(p_78088_7_);
    }*/
}
