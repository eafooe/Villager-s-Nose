package com.emilyfooe.villagersnose.capabilities.Nose;

import com.emilyfooe.villagersnose.VillagersNose;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NoseProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(INose.class)
    public static Capability<INose> NOSE_CAP = null;

    private final INose instance = NOSE_CAP.getDefaultInstance();

    @Override
    public INBT serializeNBT() {
        VillagersNose.LOGGER.info("Serializing NBT...");
        return NOSE_CAP.getStorage().writeNBT(NOSE_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        VillagersNose.LOGGER.info("Deserializing NBT...");
        NOSE_CAP.getStorage().readNBT(NOSE_CAP, instance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return NOSE_CAP.orEmpty(cap, LazyOptional.of(() -> instance));
    }
}
