package wintersteve25.rpgutils.common.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import wintersteve25.rpgutils.RPGUtils;
import wintersteve25.rpgutils.client.renderers.NPCModel;
import wintersteve25.rpgutils.client.renderers.base.GeckolibEntityRendererBase;
import wintersteve25.rpgutils.common.registry.ModEntities;
import wintersteve25.rpgutils.common.registry.ModKeybinds;

@Mod.EventBusSubscriber(modid = RPGUtils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.NPC_ENTITY.get(), (manager) -> new GeckolibEntityRendererBase<>(manager, new NPCModel()));
        ModKeybinds.register();
    }
}
