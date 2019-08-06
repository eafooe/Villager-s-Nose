package com.emilyfooe.villagersnose.capabilities;

import com.emilyfooe.villagersnose.VillagersNose;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class NoseProvider implements ICapabilitySerializable<INBT> {
    public static final ResourceLocation MY_CAPABILITY_KEY = new ResourceLocation(VillagersNose.MODID, "my_capability");

    @CapabilityInject(INose.class)
    public static Capability<INose> MY_CAPABILITY = null;

    private final LazyOptional<INose> instance = LazyOptional.of(() -> Objects.requireNonNull(MY_CAPABILITY.getDefaultInstance()));

    @Override
    public INBT serializeNBT() {
        return MY_CAPABILITY.getStorage().writeNBT(MY_CAPABILITY, MY_CAPABILITY.getDefaultInstance(), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MY_CAPABILITY.getStorage().readNBT(MY_CAPABILITY, MY_CAPABILITY.getDefaultInstance(), null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap ==  MY_CAPABILITY){
            return instance.cast();
        } else {
            return LazyOptional.empty();
        }
    }
}
