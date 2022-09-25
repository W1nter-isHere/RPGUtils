package wintersteve25.rpgutils.client.ui_creator.context;

import dev.ftb.mods.ftblibrary.ui.ContextMenu;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import wintersteve25.rpgutils.client.ui_creator.EditableWidget;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;

import java.util.List;

public class CustomContextMenu extends ContextMenu {
    
    private final EditableWidget editableWidget;
    
    public CustomContextMenu(Panel panel, EditableWidget editableWidget, List<ContextMenuItem> items) {
        super(panel, items);
        this.editableWidget = editableWidget;
        
        for (ContextMenuItem contextMenuItem : items) {
            if (contextMenuItem instanceof CustomContextMenuItem) {
                ((CustomContextMenuItem) contextMenuItem).setEditableWidget(editableWidget);
            }
        }
    }

    public EditableWidget getEditableWidget() {
        return editableWidget;
    }
}
