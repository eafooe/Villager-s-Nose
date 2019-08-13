package com.emilyfooe.villagersnose.capabilities.Nose;

import com.emilyfooe.villagersnose.VillagersNose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class NoseStorage implements Capability.IStorage<INose> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<INose> capability, INose instance, Direction side) {
        VillagersNose.LOGGER.info("Writing HasNose: " + instance.hasNose() + " to NBT");
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("HasNose", instance.hasNose());
        return nbt;
    }

    @Override
    public void readNBT(Capability<INose> capability, INose instance, Direction side, INBT nbt) {
        CompoundNBT hi = (CompoundNBT) nbt;
        instance.setHasNose(hi.getBoolean("HasNose"));
        VillagersNose.LOGGER.info("Read HasNose: " + hi.getBoolean("HasNose") + " from NBT");
        VillagersNose.LOGGER.info("HasNose is now equal to: " + instance.hasNose());
    }
}
