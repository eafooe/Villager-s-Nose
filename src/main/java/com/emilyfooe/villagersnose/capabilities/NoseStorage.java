package com.emilyfooe.villagersnose.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class NoseStorage implements Capability.IStorage<INose> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<INose> capability, INose instance, Direction side) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("HasNose", instance.getHasNose());
        return nbt;
    }

    @Override
    public void readNBT(Capability<INose> capability, INose instance, Direction side, INBT nbt) {
        CompoundNBT hi = (CompoundNBT) nbt;
        instance.setHasNose(hi.getBoolean("HasNose"));

    }
}
