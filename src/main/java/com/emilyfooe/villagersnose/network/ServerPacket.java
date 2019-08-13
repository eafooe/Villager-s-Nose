package com.emilyfooe.villagersnose.network;

import com.emilyfooe.villagersnose.VillagersNose;
import com.emilyfooe.villagersnose.capabilities.Nose.INose;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.emilyfooe.villagersnose.capabilities.Nose.NoseProvider.NOSE_CAP;

public class ServerPacket {
    private int entityId;
    private boolean hasNose;

    public ServerPacket(int entityId, boolean hasNose){
        this.entityId = entityId;
        this.hasNose = hasNose;
    }

    static void encode(ServerPacket msg, PacketBuffer buffer){
        buffer.writeInt(msg.entityId);
        buffer.writeBoolean(msg.hasNose);
    }

    static ServerPacket decode(PacketBuffer buffer){
        return new ServerPacket(buffer.readInt(), buffer.readBoolean());
    }

    static void handle(ServerPacket msg, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);

            if (!(entity instanceof VillagerEntity)){
                return;
            }

            VillagerEntity villager = (VillagerEntity) entity;
            if (villager.getCapability(NOSE_CAP, null).isPresent()){
                INose cap = villager.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
                VillagersNose.LOGGER.info("Used packet to write to entity data");

                cap.setHasNose(msg.hasNose);
                NOSE_CAP.getStorage().writeNBT(NOSE_CAP, cap, null);
                NOSE_CAP.writeNBT(cap, null);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
