package com.emilyfooe.villagersnose.capabilities.Nose;

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

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

public class NoseProvider implements ICapabilitySerializable<INBT> {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(MODID, "capability_nose");

    @CapabilityInject(INose.class)
    public static Capability<INose> NOSE_CAP = null;

    private INose instance = NOSE_CAP.getDefaultInstance();

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
