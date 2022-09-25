package wintersteve25.rpgutils.client.ui_creator.context;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.util.text.ITextComponent;
import wintersteve25.rpgutils.client.ui_creator.EditableWidget;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CustomContextMenuItem extends ContextMenuItem {
    
    private final Consumer<EditableWidget> c;
    @Nullable private final IconDrawCall iconDrawCall;
    private EditableWidget editableWidget;
    
    public CustomContextMenuItem(ITextComponent t, Icon i, Consumer<EditableWidget> c, @Nullable IconDrawCall iconDrawCall) {
        super(t, i, null);
        this.c = c;
        this.iconDrawCall = iconDrawCall;
    }

    @Override
    public void onClicked(Panel panel, MouseButton button) {
        Panel contextMenu = panel.getGui().contextMenu;
        
        if (this.closeMenu) {
            panel.getGui().closeContextMenu();
        }
        
        if (contextMenu instanceof CustomContextMenu) {
            CustomContextMenu menu = ((CustomContextMenu) contextMenu);
            c.accept(menu.getEditableWidget());
        }
    }

    @Override
    public void drawIcon(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
        if (editableWidget == null || iconDrawCall == null) {
            super.drawIcon(matrixStack, theme, x, y, w, h);
            return;
        }
        
        iconDrawCall.drawIcon(editableWidget, matrixStack, theme, x, y, w, h);
    }

    public void setEditableWidget(EditableWidget editableWidget) {
        this.editableWidget = editableWidget;
    }
}
