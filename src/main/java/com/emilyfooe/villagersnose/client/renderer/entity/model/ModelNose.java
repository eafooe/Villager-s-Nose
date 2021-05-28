package com.emilyfooe.villagersnose.client.renderer.entity.model;

import com.emilyfooe.villagersnose.item.ItemNose;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;


public class ModelNose extends BipedModel<LivingEntity> {
    public static final ModelNose INSTANCE = new ModelNose();
    public final ModelRenderer bipedNose;
    public ModelNose() {
        this(0.0F, 0.0F, 8, 6);
    }

    public ModelNose(float scaleFactor, float rotationY, int textureWidth, int textureHeight) {
        super(scaleFactor, rotationY, 0, 0);
        bipedNose = new ModelRenderer(this);
        bipedNose.setTexSize(textureWidth, textureHeight); // image x, image y
        bipedNose.addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, 0);
        bipedNose.xRot = 0.0F;
        bipedNose.yRot = 0.0F;
        bipedNose.zRot = 0.0F;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        bipedNose.xRot = head.xRot;
        bipedNose.yRot = head.yRot;
        bipedNose.zRot = head.zRot;
        return ImmutableList.of(bipedNose);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of();
    }

    public void rotate(LivingEntity entity, ItemStack itemStack) {

        if (entity instanceof AbstractClientPlayerEntity) {
            if (ItemNose.emeraldsAreNearby(entity.level, entity.getX(), entity.getY(), entity.getZ())){
                bipedNose.y = MathHelper.cos((entity.tickCount % 10) * (0.2F) * ((float) Math.PI)) * 0.03125F;

            }


        }
    }

//
//    public void wiggle(float ageInTicks){
//        float yLoc = MathHelper.cos((ageInTicks % 10) * (0.2F) * ((float) Math.PI)) * 0.03125F;
//        bipedNose.setPos(bipedNose.x, yLoc, bipedNose.z);
//    }
//
//    @Override
//    public void setupAnim(PlayerEntity p, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        VillagersNose.LOGGER.info("TEST");
//        super.setupAnim(p, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//
//
//        System.out.println("TEST");
//       //super.setupAnim(p, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//            System.out.println("This is a test...");
//
//       if (ItemNose.emeraldsAreNearby(p.level, (int) p.position().x, (int) p.position().y, (int) p.position().z)) {
//               VillagersNose.LOGGER.info("ageInTicks: " + ageInTicks);
//           VillagersNose.LOGGER.info("y position: " + (int) p.position().y);
//                bipedNose.y = MathHelper.cos((ageInTicks % 10) * (0.2F) * ((float) Math.PI)) * 0.03125F;
//            } else {
//           VillagersNose.LOGGER.info("No emeralds nearby...");
//            }
//    }

   //  If emerald ore is nearby, 'wiggle' the model by allowing its y-offset to bob up and down
//
//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public void setupRotations(AbstractClientPlayerEntity entity, MatrixStack stack, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//    System.out.println("Testing");
//         // Literally the only time I've used trig outside of school
//        if (ItemNose.emeraldsAreNearby(entity.level, (int) entity.getX(), (int) entity.getY(), (int) entity.getZ())) {
//            bipedNose.y = MathHelper.cos((ageInTicks % 10) * (0.2F) * ((float) Math.PI)) * 0.03125F;
//        }
//    }
}
