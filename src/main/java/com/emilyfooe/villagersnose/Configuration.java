package com.emilyfooe.villagersnose;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Common COMMON = new Common(BUILDER);

    public static class Common {
        public final ForgeConfigSpec.IntValue regrowthTime;
        public final ForgeConfigSpec.IntValue searchRange;
        public final ForgeConfigSpec.BooleanValue noseRegenerates;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("Common Settings");
            regrowthTime = builder
                    .comment("The time, in seconds, to regrow a villager's nose. The default is 900 seconds, i.e. 15 minutes")
                    .defineInRange("noseRegrowthTime", 900, 30, 86400);

            searchRange = builder
                    .comment("The range, in blocks, to search for emeralds. The default is 3 blocks")
                    .defineInRange("emeraldSearchRange", 3, 1, 8);

            noseRegenerates = builder
                    .comment("Whether a villager's nose should grow back after it is removed. The default is true")
                    .define("canNoseRegenerate", true);
            builder.pop();
        }


    }

    static final ForgeConfigSpec SPEC = BUILDER.build();
}
