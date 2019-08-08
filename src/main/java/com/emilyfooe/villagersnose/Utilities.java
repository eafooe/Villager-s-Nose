package com.emilyfooe.villagersnose;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class Utilities {
    private static final List<SoundEvent> villagerSounds = createVillagerSoundsList();

    private static List<SoundEvent> createVillagerSoundsList(){
        List<SoundEvent> list = new LinkedList<>();
        list.add(SoundEvents.ENTITY_VILLAGER_NO);
        list.add(SoundEvents.ENTITY_VILLAGER_YES);
        list.add(SoundEvents.ENTITY_VILLAGER_TRADE);
        list.add(SoundEvents.ENTITY_VILLAGER_AMBIENT);
        return list;
    }

    public static SoundEvent getRandomVillagerSound(){
        return villagerSounds.get(new Random().nextInt(villagerSounds.size()));
    }
}
