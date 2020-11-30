package com.northwestwind.moreboots.handler;

import com.northwestwind.moreboots.handler.packet.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MoreBootsPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("moreboots", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static int ID = 0;

    public static void registerPackets() {
        INSTANCE.registerMessage(ID++, CPlayerEnderTeleportPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(Utils.objToBytes(packet)), packetBuffer -> (CPlayerEnderTeleportPacket) Utils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CPlayerSkatePacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(Utils.objToBytes(packet)), packetBuffer -> (CPlayerSkatePacket) Utils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
    }
}
