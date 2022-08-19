package wintersteve25.rpgutils.client.ui.quests.player;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.NordButton;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.ButtonListBaseScreen;
import net.minecraft.client.Minecraft;
import wintersteve25.rpgutils.common.data.loaded.quest.Quest;
import wintersteve25.rpgutils.common.registry.ModCapabilities;

public class AvailableQuestsPanel extends ButtonListBaseScreen {

    public AvailableQuestsPanel() {
        
    }

    @Override
    public void addButtons(Panel panel) {
        Minecraft.getInstance().player.getCapability(ModCapabilities.PLAYER_QUEST).ifPresent(cap -> {
            for (Quest quest : cap.getAvailableQuests()) {
                panel.add(new NordButton(panel, quest.getTitle(), Icon.EMPTY) {
                    @Override
                    public void onClicked(MouseButton mouseButton) {
                        
                    }
                });
            }
        });
    }
}
