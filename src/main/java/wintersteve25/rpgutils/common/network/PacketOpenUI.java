package wintersteve25.rpgutils.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.rpgutils.client.ui_creator.UIProfileWrapper;

import java.util.function.Supplier;

public class PacketOpenUI implements ModPacket {

    private final String name;

    public PacketOpenUI(String name) {
        this.name = name;
    }

    public PacketOpenUI(PacketBuffer buffer) {
        name = buffer.readUtf();
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeUtf(name);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> UIProfileWrapper.open(name));
        ctx.get().setPacketHandled(true);
    }
}
