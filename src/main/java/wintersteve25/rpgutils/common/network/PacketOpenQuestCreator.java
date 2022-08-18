package wintersteve25.rpgutils.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.rpgutils.client.ui.quests.QuestCreatorUI;

import java.util.function.Supplier;

public class PacketOpenQuestCreator implements ModPacket {
    @Override
    public void encode(PacketBuffer buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(QuestCreatorUI::open);
        ctx.get().setPacketHandled(true);
    }
}
