package wintersteve25.rpgutils.client.ui_creator.context;

import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.ContextMenu;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import net.minecraft.util.text.StringTextComponent;
import wintersteve25.rpgutils.client.ui_creator.EditableWidget;
import wintersteve25.rpgutils.client.ui_creator.UICreatorUI;
import wintersteve25.rpgutils.client.ui_creator.widget.NewWidgetScreen;

import java.util.ArrayList;

public class ClickBlankContextMenu extends ContextMenu {
    
    public ClickBlankContextMenu(UICreatorUI panel, int pressedAtX, int pressedAtY) {
        super(panel, new ArrayList<>());
        
        items.add(new ContextMenuItem(new StringTextComponent("Add"), Icons.ADD, () -> {
            new NewWidgetScreen((customId, type) -> {
                panel.createWidget(customId, type, pressedAtX, pressedAtY);
                panel.openGui();
            }).openGui();
        }));
        
        items.add(new ContextMenuItem(new StringTextComponent("Toggle Preview"), Icons.CAMERA, () -> {
            panel.widgets.forEach((id, widget) -> {
                if (widget instanceof EditableWidget) {
                    EditableWidget editableWidget = ((EditableWidget) widget);
                    editableWidget.setPreviewing(!editableWidget.isPreviewing());
                }
            });
            
            panel.refreshWidgets();
        }));
    
        hasIcons = true;
    }
}
