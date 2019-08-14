package com.emilyfooe.villagersnose.network;

import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.capabilities.Nose.INose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;

public class ClientPacket {
    private int entityId;

    public ClientPacket(int entityId){
        this.entityId = entityId;
    }

    static void encode(ClientPacket msg, PacketBuffer buffer){
        buffer.writeInt(msg.entityId);
        VillagersNose.LOGGER.info("Wrote entityId: " + buffer.readInt());
    }

    static ClientPacket decode(PacketBuffer buffer){
        int entityId = buffer.readInt();
        VillagersNose.LOGGER.info("Read entityId: " + entityId);
        return new ClientPacket(entityId);
    }

    // Send from server to client
    static void handle(ClientPacket msg, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity serverPlayer = ctx.get().getSender();
            ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;

            Entity serverVillager = null;
            if (serverPlayer != null) {
                serverVillager = serverPlayer.world.getEntityByID(msg.entityId);
            }
            Entity clientVillager = clientPlayer.world.getEntityByID(msg.entityId);

            if (serverVillager != null && serverVillager.getCapability(NOSE_CAP).isPresent()) {
                INose serverCap = serverVillager.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
                boolean hasNose = serverCap.hasNose();
                VillagersNose.LOGGER.info("Server hasNose: " + hasNose);
                if (clientVillager != null && clientVillager.getCapability(NOSE_CAP).isPresent()) {
                    INose clientCap = clientVillager.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
                    clientCap.setHasNose(hasNose);
                    VillagersNose.LOGGER.info("Client hasNose: " + clientCap.hasNose());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
