package com.emilyfooe.villagersnose;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Common COMMON = new Common(BUILDER);
    public static class Common{
        public final ForgeConfigSpec.IntValue regrowthTime;
        public final ForgeConfigSpec.IntValue searchRange;

        Common(ForgeConfigSpec.Builder builder){
            builder.push("Common Settings");
            regrowthTime = builder
                    .comment("The time, in seconds, to regrow a villager's nose")
                    .comment("The default is 900 seconds, i.e. 15 minutes")
                    .defineInRange("min", 900, 30, 86400);

            searchRange = builder
                    .comment("The range, in blocks, to search for emeralds")
                    .comment("The default is 5 blocks")
                    .defineInRange("min", 5, 0, 24);
            builder.pop();
        }


    }
    static final ForgeConfigSpec SPEC = BUILDER.build();
}
