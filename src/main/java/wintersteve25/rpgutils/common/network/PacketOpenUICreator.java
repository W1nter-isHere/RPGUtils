package wintersteve25.rpgutils.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.rpgutils.client.ui.quests.creator.QuestCreatorUI;
import wintersteve25.rpgutils.client.ui_creator.UICreatorUI;

import java.util.function.Supplier;

public class PacketOpenUICreator implements ModPacket {
    
    private final String name;

    public PacketOpenUICreator(String name) {
        this.name = name;
    }
    
    public PacketOpenUICreator(PacketBuffer buffer) {
        name = buffer.readUtf();
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeUtf(name);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> UICreatorUI.open(name));
        ctx.get().setPacketHandled(true);
    }
}
