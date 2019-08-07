package com.emilyfooe.villagersnose.capabilities.Timer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TimerStorage implements Capability.IStorage<ITimer> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<ITimer> capability, ITimer instance, Direction side) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("RegrowthTime", instance.getTimer());
        return nbt;
    }

    @Override
    public void readNBT(Capability<ITimer> capability, ITimer instance, Direction side, INBT nbt) {
        instance.setTimer(((CompoundNBT) nbt).getInt("RegrowthTime"));
    }
}
