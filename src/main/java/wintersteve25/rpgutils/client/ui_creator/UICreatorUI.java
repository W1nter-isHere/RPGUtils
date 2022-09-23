package wintersteve25.rpgutils.client.ui_creator;

import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import wintersteve25.rpgutils.client.ui_creator.context.ClickBlankContextMenu;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;

import java.util.function.Supplier;

public class UICreatorUI extends BaseScreen {
    
    private final UIProfile uiProfile;
    
    public UICreatorUI() {
        uiProfile = new UIProfile();
    }
    
    @Override
    public void addWidgets() {
    }
    
    private Widget addNewWidget(String id, Supplier<Widget> widgetCreator, int x, int y) {
        Widget widget = widgetCreator.get();
        widget.setSize(20, 20);
        widget.setPos(x, y);
        
        uiProfile.createOrReplaceWidget(id, widget);
        add(widget);
        
        return widget;
    }

    @Override
    public boolean mousePressed(MouseButton button) {
        if (button.isRight()) {
            if (!isMouseOverAnyWidget()) {
                openContextMenu(new ClickBlankContextMenu(this, getMouseX(), getMouseY()`));
            }
        }
        
        return super.mousePressed(button);
    }

    public static void open() {
        new UICreatorUI().openGui();
    }
}    
