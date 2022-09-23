package wintersteve25.rpgutils.client.ui_creator.context;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ContextMenu;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import net.minecraft.util.text.StringTextComponent;
import wintersteve25.rpgutils.client.ui.quests.creator.TextInputPrompt;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ContextPanelBuilder {
    private final List<ContextMenuItem> items;
    
    public ContextPanelBuilder() {
        items = new ArrayList<>();
    }
    
    public void rename(Button renameTarget) {
        items.add(new ContextMenuItem(new StringTextComponent("Rename"), Icon.EMPTY, () -> {
            TextInputPrompt prompt = new TextInputPrompt();
            prompt.enable();
            prompt.openGui();
        }));
    }
    
    public Function<Panel, Panel> build() {
        return panel -> new ContextMenu(panel, items);
    }
}
