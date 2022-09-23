package wintersteve25.rpgutils.client.ui_creator.context;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.*;
import net.minecraft.util.text.StringTextComponent;

public class ClickBlankContextMenu extends Panel {
    
    private final int pressedAtX;
    private final int pressedAtY;
    
    private final Button add;
    
    public ClickBlankContextMenu(Panel panel, int pressedAtX, int pressedAtY) {
        super(panel);
        this.pressedAtX = pressedAtX;
        this.pressedAtY = pressedAtY;
        this.add = new SimpleButton(this, new StringTextComponent("Add"), Icons.ADD_GRAY, (btn, mouse) -> {
            
        });
    }

    @Override
    public void addWidgets() {
        add();
    }

    @Override
    public void drawBackground(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
        theme.drawPanelBackground(matrixStack, x, y, w, h);
    }

    @Override
    public void alignWidgets() {
        align(new WidgetLayout.Vertical(0, 2, 0));
    }
}
