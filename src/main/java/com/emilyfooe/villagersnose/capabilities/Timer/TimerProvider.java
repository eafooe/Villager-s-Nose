package com.emilyfooe.villagersnose.capabilities.Timer;

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

public class TimerProvider implements ICapabilitySerializable<INBT> {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(MODID, "capability_timer");

    @CapabilityInject(ITimer.class)
    public static Capability<ITimer> TIMER_CAP = null;

    private ITimer instance = TIMER_CAP.getDefaultInstance();
    private final LazyOptional<ITimer> holder = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == TIMER_CAP) {
            return holder.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return TIMER_CAP.getStorage().writeNBT(TIMER_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        TIMER_CAP.getStorage().readNBT(TIMER_CAP, instance, null, nbt);
    }
}
