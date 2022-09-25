package wintersteve25.rpgutils.client.ui_creator.widget.widgets;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.util.text.ITextComponent;
import wintersteve25.rpgutils.client.ui_creator.IUIProfileUI;

public class ButtonWidget extends SimpleTextButton {
    
    private final String customId;
    
    public ButtonWidget(Panel panel, ITextComponent txt, Icon icon, String customId) {
        super(panel, txt, icon);
        this.customId = customId;
    }

    @Override
    public void onClicked(MouseButton mouseButton) {
        Widget base = parent.getGui();
        if (base instanceof IUIProfileUI) {
            ((IUIProfileUI) base).onButtonClicked(this, mouseButton, customId);
        }
    }
}
