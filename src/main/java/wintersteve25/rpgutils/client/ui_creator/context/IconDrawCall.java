package wintersteve25.rpgutils.client.ui_creator.context;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.ftb.mods.ftblibrary.ui.Theme;
import wintersteve25.rpgutils.client.ui_creator.EditableWidget;

@FunctionalInterface
public interface IconDrawCall {
    void drawIcon(EditableWidget editableWidget, MatrixStack matrixStack, Theme theme, int x, int y, int w, int h);
}
