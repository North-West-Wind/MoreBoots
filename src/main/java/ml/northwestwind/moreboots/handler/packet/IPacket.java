package ml.northwestwind.moreboots.handler.packet;

import net.minecraftforge.network.NetworkEvent;

import java.io.Serializable;

public interface IPacket extends Serializable {
    void handle(final NetworkEvent.Context ctx);
}
