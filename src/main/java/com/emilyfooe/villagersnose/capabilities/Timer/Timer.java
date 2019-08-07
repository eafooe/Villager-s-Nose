package com.emilyfooe.villagersnose.capabilities.Timer;

public class Timer implements ITimer {
    private int ticks;

    @Override
    public int getTimer() {
        return ticks;
    }

    @Override
    public void setTimer(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public void decrementTimer() {
        ticks--;
    }
}
