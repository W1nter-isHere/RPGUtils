package wintersteve25.rpgutils.client.ui_creator.widget;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Panel;
import wintersteve25.rpgutils.client.ui.components.SelectTypePanel;
import wintersteve25.rpgutils.client.ui.components.SelectableType;
import wintersteve25.rpgutils.client.ui_creator.widget.type.WidgetType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectWidgetPanel extends SelectTypePanel<WidgetType> {

    public SelectWidgetPanel(Panel panel) {
        super(Arrays.stream(WidgetType.values()).map(widgetType -> new SelectableType<>(Icon.EMPTY, widgetType.getDisplayName(), (btn, mouse) -> {
        }, widgetType)).collect(Collectors.toList()), panel);
    }
}
