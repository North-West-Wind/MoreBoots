package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.handler.packet.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

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
        registerPacket(CPlayerEnderTeleportPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CPlayerSkatePacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CPlayerKAPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CPlayerMultiJumpPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(COpenStorageBootsPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CShootDragonBallPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CShootWitherSkullPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CActivateBootsPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CStrikeAreaPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CThrowTNTPacket.class, NetworkDirection.PLAY_TO_SERVER);
        registerPacket(CPlayerRandomTeleportPacket.class, NetworkDirection.PLAY_TO_SERVER);
    }

    private static <P extends IPacket> void registerPacket(Class<P> clazz, NetworkDirection direction) {
        INSTANCE.registerMessage(ID++, clazz, (packet, packetBuffer) -> packetBuffer.writeByteArray(Utils.packetToBytes(packet)), packetBuffer -> Utils.bytesToObj(packetBuffer.readByteArray()), (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.handle(ctx.get()));
            ctx.get().setPacketHandled(true);
        }, Optional.of(direction));
    }
}
