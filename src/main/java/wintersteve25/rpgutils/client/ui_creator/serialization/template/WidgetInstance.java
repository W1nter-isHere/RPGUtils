package wintersteve25.rpgutils.client.ui_creator.serialization.template;

import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Widget;
import wintersteve25.rpgutils.client.ui_creator.EditableWidget;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;
import wintersteve25.rpgutils.client.ui_creator.widget.type.WidgetType;

public class WidgetInstance {
    
    private final WidgetType widgetType;
    private final String customId;
    
    private int x;
    private int y;
    private int width;
    private int height;

    public WidgetInstance(WidgetType widgetType, String customId, int x, int y, int width, int height) {
        this.widgetType = widgetType;
        this.customId = customId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public WidgetType getWidgetType() {
        return widgetType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getCustomId() {
        return customId;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public Widget create(Panel parent) {
        Widget widget = getWidgetType().getWidgetConstructor().create(parent, customId);
        widget.setPos(getX(), getY());
        widget.setSize(getWidth(), getHeight());
        return widget;
    }

    public EditableWidget createEditable(Panel parent, UIProfile uiProfile) {
        return new EditableWidget(parent, uiProfile, getCustomId(), this);
    }
}
