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
        INSTANCE.registerMessage(ID++, CLivingFreezeLavaPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingFreezeLavaPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingSolidifyWaterPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingSolidifyWaterPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingRainbowSocksPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingRainbowSocksPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingSocksPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingSocksPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingRepairBootsPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingRepairBootsPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingIlluminateSurroundingsPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingIlluminateSurroundingsPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingFreezeWaterPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingFreezeWaterPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CRemoveNegativeEffectsPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CRemoveNegativeEffectsPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingWarpPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingWarpPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingClimbPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingClimbPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingHastePacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingHastePacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
        INSTANCE.registerMessage(ID++, CLivingMoonJumpPacket.class, (packet, packetBuffer) -> packetBuffer.writeByteArray(ByteBufUtils.objToBytes(packet)), packetBuffer -> (CLivingMoonJumpPacket) ByteBufUtils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        });
    }
}
