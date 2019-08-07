package com.emilyfooe.villagersnose;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    static final Common COMMON = new Common(BUILDER);
    public static class Common{
        final ForgeConfigSpec.IntValue regrowthTime;

        Common(ForgeConfigSpec.Builder builder){
            builder.push("Common Settings");
            regrowthTime = builder
                    .comment("The time, in seconds, to regrow a villager's nose")
                    .comment("The default is 900 seconds, i.e. 15 minutes")
                    .defineInRange("min", 900, 30, 86400);
            builder.pop();
        }
    }
    static final ForgeConfigSpec SPEC = BUILDER.build();
}
