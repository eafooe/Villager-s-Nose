package com.emilyfooe.villagersnose.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;


public class ModelWitchNose extends ModelNose {
    public static final ModelWitchNose INSTANCE = new ModelWitchNose();
    private final ModelRenderer mole = (new ModelRenderer(this)).setTexSize(12, 6);

    private ModelWitchNose() {
        super(0, 0, 12, 6);
        mole.texOffs(8, 0);
        mole.setTexSize(4, 2);
        mole.addBox(0.0F, -1.0F, -6.75F, 1, 1, 1, -0.25F);
        bipedNose.addChild(mole);
    }

}
