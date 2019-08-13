package com.emilyfooe.villagersnose.network;

import com.emilyfooe.villagersnose.VillagersNose;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int id = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(VillagersNose.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register(){
        INSTANCE.registerMessage(id++, ServerPacket.class, ServerPacket::encode, ServerPacket::decode, ServerPacket::handle);
        INSTANCE.registerMessage(id++, ClientPacket.class, ClientPacket::encode, ClientPacket::decode, ClientPacket::handle);
    }

}
