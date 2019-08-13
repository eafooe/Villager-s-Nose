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

public class ClientPacket {
    private int entityId;

    public ClientPacket(int entityId){
        this.entityId = entityId;
    }

    static void encode(ClientPacket msg, PacketBuffer buffer){
        buffer.writeInt(msg.entityId);
    }

    static ClientPacket decode(PacketBuffer buffer){
        return new ClientPacket(buffer.readInt());
    }

    static void handle(ClientPacket msg, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);

            if (!(entity instanceof VillagerEntity)){
                return;
            }

            VillagerEntity villager = (VillagerEntity) entity;
            if (villager.getCapability(NOSE_CAP, null).isPresent()){
                INose cap = villager.getCapability(NOSE_CAP).orElseThrow(NullPointerException::new);
                VillagersNose.LOGGER.info("Used packet to write to entity data");

                NOSE_CAP.getStorage().writeNBT(NOSE_CAP, cap, null);
                NOSE_CAP.writeNBT(cap, null);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
