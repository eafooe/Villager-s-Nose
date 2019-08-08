package com.emilyfooe.villagersnose.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.LinkedList;
import java.util.List;

import static com.emilyfooe.villagersnose.VillagersNose.MODID;

public class ModSounds {
    public static List<SoundEvent> sounds = new LinkedList<>();
    public static SoundEvent SOUND_EMERALD = createSound("music.emerald");

    private static SoundEvent createSound(String name){
        ResourceLocation location = new ResourceLocation(MODID, name);
        SoundEvent sound = new SoundEvent(location).setRegistryName(location);
        sounds.add(sound);
        return sound;
    }
}
