package com.emilyfooe.villagersnose.network;

import com.emilyfooe.villagersnose.VillagersNose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;

public class ClientPacket {
    private int entityId;
    private boolean hasNose;

    public ClientPacket(int entityId, boolean hasNose){
        this.entityId = entityId;
        this.hasNose = hasNose;
    }

    static void encode(ClientPacket msg, PacketBuffer buffer){
        buffer.writeBoolean(msg.hasNose);
        buffer.writeInt(msg.entityId);
        //VillagersNose.LOGGER.info("Wrote hasNose: " + buffer.readBoolean());
        //VillagersNose.LOGGER.info("Wrote entityId: " + buffer.readInt());
    }

    static ClientPacket decode(PacketBuffer buffer){
        boolean hasNose = buffer.readBoolean();
        int entityId = buffer.readInt();
        VillagersNose.LOGGER.info("Read hasNose: " + hasNose);
        VillagersNose.LOGGER.info("Read entityId: " + entityId);
        return new ClientPacket(entityId, hasNose);
    }

    // Send from server to client
    static void handle(ClientPacket msg, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            // Get instance of entity in client's world
            ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;
            Entity entity = clientPlayer.world.getEntityByID(msg.entityId);

            // If the entity can be found, and it has our nose capability, set the value of 'hasNose'
            if (entity != null && entity.getCapability(NOSE_CAP).isPresent()){
                entity.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new).setHasNose(msg.hasNose);
                VillagersNose.LOGGER.info("Set hasNose in handle method");
            } else {
                throw new RuntimeException("Something bad happened");
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
