package wintersteve25.rpgutils.client.ui_creator.serialization.template;

import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Widget;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.rpgutils.client.ui_creator.widget.type.WidgetType;

public class ButtonWidgetInstance extends WidgetInstance {
    private String title;
    
    public ButtonWidgetInstance(WidgetType widgetType, String customId, int x, int y, int width, int height, String title) {
        super(widgetType, customId, x, y, width, height);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Widget create(Panel parent) {
        Widget widget = super.create(parent);
        if (widget instanceof Button) {
            ((Button) widget).setTitle(new TranslationTextComponent(title));
        }
        return widget;
    }
}
