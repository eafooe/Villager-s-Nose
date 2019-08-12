package com.emilyfooe.villagersnose.capabilities.Nose;

import com.emilyfooe.villagersnose.VillagersNose;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class NoseProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(INose.class)
    public static Capability<INose> NOSE_CAP = null;

    private final LazyOptional<INose> instance = LazyOptional.of(() -> Objects.requireNonNull(NOSE_CAP.getDefaultInstance()));

    @Override
    public INBT serializeNBT() {
        return NOSE_CAP.getStorage().writeNBT(NOSE_CAP, NOSE_CAP.getDefaultInstance(), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        NOSE_CAP.getStorage().readNBT(NOSE_CAP, NOSE_CAP.getDefaultInstance(), null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == NOSE_CAP){
            return instance.cast();
        }
        return LazyOptional.empty();
    }
}
