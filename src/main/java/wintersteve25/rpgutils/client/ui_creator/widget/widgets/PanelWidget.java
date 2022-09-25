package wintersteve25.rpgutils.client.ui_creator.widget.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import wintersteve25.rpgutils.client.ui_creator.widget.LayoutOptions;

import java.util.function.Consumer;

public class PanelWidget extends Panel {

    private final String customId;
    private Consumer<Panel> addWidgets;
    private LayoutOptions layout;
    
    public PanelWidget(Panel panel, String customId) {
        super(panel);
        this.customId = customId;
    }

    @Override
    public void addWidgets() {
        if (addWidgets == null) return;
        addWidgets.accept(this);
    }

    @Override
    public void alignWidgets() {
        if (layout == null) return;
        align(layout.get());
    }

    @Override
    public void drawBackground(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
        theme.drawPanelBackground(matrixStack, x, y, w - 10, h - 10);
    }

    public void setLayout(LayoutOptions layout) {
        this.layout = layout;
        refreshWidgets();
    }

    public void setAddWidgets(Consumer<Panel> addWidgets) {
        this.addWidgets = addWidgets;
        refreshWidgets();
    }
}
