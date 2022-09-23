package wintersteve25.rpgutils.client.ui_creator.wiget.type;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class WidgetType {
    public static final List<WidgetType> ALL_TYPES;
    
    static {
        ALL_TYPES = new ArrayList<>();
        ALL_TYPES.add(new WidgetType("Simple Text Button", panel -> new SimpleTextButton(panel, new StringTextComponent("Simple Text Button"), Icon.EMPTY) {
            @Override
            public void onClicked(MouseButton mouseButton) {
            }
        }, null));
    }
    
    private final String serializedName;
    private final Function<Panel, Widget> widgetConstructor;
    @Nullable
    private final Panel editContextPanel;
    private final int defaultWidth;
    private final int defaultHeight;
    
    public WidgetType(String serializedName, Function<Panel, Widget> widgetConstructor, @Nullable Panel editContextPanel, int defaultWidth, int defaultHeight) {
        this.serializedName = serializedName;
        this.widgetConstructor = widgetConstructor;
        this.editContextPanel = editContextPanel;
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
    }
    
    public WidgetType(String serializedName, Function<Panel, Widget> widgetConstructor, @Nullable Panel editContextPanel) {
        this(serializedName, widgetConstructor, editContextPanel, 20, 20);
    }
}