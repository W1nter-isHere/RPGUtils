package wintersteve25.rpgutils.client.ui_creator.widget.type;

import net.minecraft.util.text.ITextComponent;
import wintersteve25.rpgutils.client.ui_creator.context.CustomContextMenuSupplier;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.WidgetInstance;

import javax.annotation.Nullable;

public interface IWidgetType {
    ITextComponent getDisplayName();

    WidgetSupplier getWidgetConstructor();
    
    @Nullable
    CustomContextMenuSupplier getEditContextPanel();
    
    int getDefaultWidth();

    int getDefaultHeight();
    
    WidgetInstance createInstance(String customId, int x, int y);
}
