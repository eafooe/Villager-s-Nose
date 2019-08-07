package com.emilyfooe.villagersnose.capabilities.Nose;

public class Nose implements INose {
    private boolean hasNose = true;

    @Override
    public boolean getHasNose() {
        return hasNose;
    }

    @Override
    public void setHasNose(boolean hasNose) {
        this.hasNose = hasNose;
    }
}
