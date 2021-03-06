package com.emilyfooe.villagersnose.capabilities.Nose;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

public class NoseProvider implements ICapabilitySerializable<INBT> {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(MODID, "capability_nose");

    @CapabilityInject(INose.class)
    public static Capability<INose> NOSE_CAP = null;

    private INose instance = NOSE_CAP.getDefaultInstance();
    private final LazyOptional<INose> holder = LazyOptional.of(() -> instance);

    @Override
    public INBT serializeNBT() {
        return NOSE_CAP.getStorage().writeNBT(NOSE_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        NOSE_CAP.getStorage().readNBT(NOSE_CAP, instance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == NOSE_CAP) {
            return holder.cast();
        }
        return LazyOptional.empty();
    }
}
