package wintersteve25.rpgutils.client.ui_creator.serialization.template;

import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Widget;
import wintersteve25.rpgutils.client.ui_creator.EditableWidget;
import wintersteve25.rpgutils.client.ui_creator.UICreatorUI;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;
import wintersteve25.rpgutils.client.ui_creator.widget.LayoutOptions;
import wintersteve25.rpgutils.client.ui_creator.widget.type.WidgetType;
import wintersteve25.rpgutils.client.ui_creator.widget.widgets.PanelWidget;

import java.util.List;
import java.util.stream.Collectors;

public class PanelWidgetInstance extends WidgetInstance {

    private final List<WidgetInstance> children;
    private LayoutOptions layout;

    public PanelWidgetInstance(WidgetType widgetType, List<WidgetInstance> children, String customId, int x, int y, int width, int height) {
        super(widgetType, customId, x, y, width, height);
        this.children = children;
    }

    public List<WidgetInstance> getChildren() {
        return children;
    }

    public LayoutOptions getLayout() {
        return layout;
    }

    public void setLayout(LayoutOptions layout) {
        this.layout = layout;
    }

    @Override
    public Widget create(Panel parent) {
        Widget widget = super.create(parent);
        
        if (widget instanceof PanelWidget) {
            PanelWidget panelWidget = (PanelWidget) widget; 
            panelWidget.setLayout(getLayout());
            panelWidget.setAddWidgets(panel -> panel.widgets.addAll(getChildren().stream().map(widgetTemplate -> widgetTemplate.create(panel)).collect(Collectors.toList())));
        }
        
        return widget;
    }

    @Override
    public EditableWidget createEditable(Panel parent, UIProfile uiProfile) {
        Widget widget = super.create(parent);

        if (widget instanceof PanelWidget) {
            PanelWidget panelWidget = (PanelWidget) widget;
            panelWidget.setLayout(getLayout());
            panelWidget.setAddWidgets(panel -> panel.widgets.addAll(getChildren().stream().map(widgetTemplate -> new EditableWidget(panel, uiProfile, getCustomId(), widgetTemplate)).collect(Collectors.toList())));
        }

        return new EditableWidget(parent, getWidgetType(), widget, uiProfile, getCustomId());
    }
}
