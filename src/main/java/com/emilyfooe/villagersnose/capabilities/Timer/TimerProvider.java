package com.emilyfooe.villagersnose.capabilities.Timer;

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

public class TimerProvider implements ICapabilitySerializable<INBT> {
    public static final ResourceLocation TIMER_CAP_KEY = new ResourceLocation(VillagersNose.MODID, "capability_timer");

    @CapabilityInject(ITimer.class)
    public static Capability<ITimer> TIMER_CAP = null;

    private final LazyOptional<ITimer> instance = LazyOptional.of(() -> Objects.requireNonNull(TIMER_CAP.getDefaultInstance()));

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap ==  TIMER_CAP){
            return instance.cast();
        } else {
            return LazyOptional.empty();
        }
    }

    @Override
    public INBT serializeNBT() {
        return TIMER_CAP.getStorage().writeNBT(TIMER_CAP, TIMER_CAP.getDefaultInstance(), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        TIMER_CAP.getStorage().readNBT(TIMER_CAP, TIMER_CAP.getDefaultInstance(), null, nbt);
    }
}
