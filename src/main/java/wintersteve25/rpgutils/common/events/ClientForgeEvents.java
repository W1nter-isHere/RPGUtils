package wintersteve25.rpgutils.common.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wintersteve25.rpgutils.RPGUtils;

@Mod.EventBusSubscriber(modid = RPGUtils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {
    
    @SubscribeEvent
    public static void clientRenderHud(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
        }
    }
}
