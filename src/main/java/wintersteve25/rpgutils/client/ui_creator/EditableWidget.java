package wintersteve25.rpgutils.client.ui_creator;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.ftb.mods.ftblibrary.ui.CursorType;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.client.Minecraft;
import wintersteve25.rpgutils.client.ui_creator.context.CustomContextMenuSupplier;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.WidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.widget.type.WidgetType;

import javax.annotation.Nullable;

public class EditableWidget extends Panel {

    private final WidgetType widgetType;
    private final Widget widget;
    private final UIProfile uiProfile;
    private final String customId;

    private boolean resizing;
    private boolean moving;
    private boolean previewing;
    
    private int initialMX;
    private int initialMY;
    private HoldState holding;
    
    public EditableWidget(Panel panel, UIProfile uiProfile, String customId, WidgetInstance widget) {
        super(panel);
        this.uiProfile = uiProfile;
        this.widgetType = widget.getWidgetType();
        this.widget = widget.create(this);
        this.widget.setPos(0, 0);
        this.customId = customId;
        setSize(widget.getWidth(), widget.getHeight());
        setPos(widget.getX(), widget.getY());
    }

    public EditableWidget(Panel panel, WidgetType widgetType, Widget widget, UIProfile uiProfile, String customId) {
        super(panel);
        this.widgetType = widgetType;
        this.widget = widget;
        this.widget.parent = this;
        this.uiProfile = uiProfile;
        this.customId = customId;
        setSize(widget.width, widget.height);
        setPos(widget.getX(), widget.getY());
    }

    @Override
    public void addWidgets() {
        add(widget);
    }

    @Override
    public void alignWidgets() {
    }

    public void setResizing(boolean resizing) {
        this.resizing = resizing;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setPreviewing(boolean previewing) {
        this.previewing = previewing;
    }

    public boolean isResizing() {
        return resizing;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isPreviewing() {
        return previewing;
    }

    public UIProfile getUiProfile() {
        return uiProfile;
    }

    public String getCustomId() {
        return customId;
    }

    @Override
    public boolean shouldAddMouseOverText() {
        return previewing && !resizing && !moving;
    }

    @Override
    public void tick() {
        super.tick();
        
        if (resizing) {
            if (isMouseButtonDown(MouseButton.LEFT)) {
                int mx = getMouseX();
                int my = getMouseY();

                boolean right = resizeRight(mx);
                boolean bottom = resizeBottom(my);
                
                if (right && bottom) {
                    int w = width + (mx - initialMX);
                    widget.setWidth(w);
                    setWidth(w);
                    initialMX = mx;
                    int h = height + (my - initialMY);
                    widget.setHeight(h);
                    setHeight(h);
                    initialMY = my;
                    holding = HoldState.RIGHT_BOTTOM;
                } else {
                    if (right) {
                        int w = width + (mx - initialMX);
                        widget.setWidth(w);
                        setWidth(w);
                        initialMX = mx;
                        holding = HoldState.RIGHT;
                    }

                    if (bottom) {
                        int h = height + (my - initialMY);
                        widget.setHeight(h);
                        setHeight(h);
                        initialMY = my;
                        holding = HoldState.BOTTOM;
                    }
                }
            }
        }
        
        if (moving) {
            if (isMouseButtonDown(MouseButton.LEFT)) {
                setX(getMouseX());
                setY(getMouseY());
            }
        }
    }

    @Override
    public boolean mousePressed(MouseButton button) {
        if (widget instanceof Panel) {
            for (Widget w : ((Panel) widget).widgets) {
                if (w instanceof EditableWidget && w.isMouseOver()) {
                    return w.mousePressed(button);
                }
            }
        }
        
        CustomContextMenuSupplier contextMenu = widgetType.getEditContextPanel();

        if (contextMenu != null) {
            if (button.isRight()) {
                if (!previewing && isMouseOver()) {
                    openContextMenu(contextMenu.create(this.getGui(), this));
                    return true;
                }
            } else if (button.isLeft()) {
                if (!isMouseOverAnyWidget()) {
                    closeContextMenu();
                    return true;
                }
            }
        }
        
        if (button.isLeft()) {
            if (previewing && !resizing && !moving) {
                widget.mousePressed(button);
                return true;
            }
            
            if (resizing) {
                initialMX = getMouseX();
                initialMY = getMouseY();
            }
        }
        
        return false;
    }

    @Override
    public void mouseReleased(MouseButton button) {
        super.mouseReleased(button);
        
        if (moving) {
            uiProfile.modifyChild(customId, false, instance -> {
                instance.setX(getX());
                instance.setY(getY());
            });
        }
        
        if (resizing) {
            uiProfile.modifyChild(customId, false, instance -> {
                instance.setWidth(width);
                instance.setHeight(height);
            });
            
            holding = HoldState.RELEASED;
        }
    }

    @Override
    public @Nullable CursorType getCursor() {
        if (resizing) {
            int mx = getMouseX();
            int my = getMouseY();

            boolean left = resizeLeft(mx);
            boolean right = resizeRight(mx);
            boolean top = resizeTop(my);
            boolean bottom = resizeBottom(my);
            
            if (left && top || holding == HoldState.LEFT_TOP) {
                return CursorType.CROSSHAIR;
            }
            
            if (left && bottom || holding == HoldState.LEFT_BOTTOM) {
                return CursorType.CROSSHAIR;
            }
            
            if (right && top || holding == HoldState.RIGHT_TOP) {
                return CursorType.CROSSHAIR;
            }
            
            if (right && bottom || holding == HoldState.RIGHT_BOTTOM) {
                return CursorType.CROSSHAIR;
            }
            
            if (left || right) {
                return CursorType.HRESIZE;
            }

            if (top || bottom) {
                return CursorType.VRESIZE;
            }
        }
        
        if (moving) {
            return CursorType.CROSSHAIR;
        }

        return CursorType.HAND;
    }

    @Override
    public void drawBackground(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
//        theme.drawPanelBackground(matrixStack, x, y, w, h);
    }

    private boolean resizeLeft(int mx) {
        return (mx >= getX() && mx <= getX() + 5) || holding == HoldState.LEFT;
    }
    
    private boolean resizeRight(int mx) {
        int rightEdge = getX() + width;
        return (mx >= rightEdge - 5 && mx <= rightEdge) || holding == HoldState.RIGHT;
    }
    
    private boolean resizeTop(int my) {
        return (my >= getY() && my <= getY() + 5) || holding == HoldState.TOP;
    }
    
    private boolean resizeBottom(int my) {
        int bottomEdge = getY() + height;
        return my >= bottomEdge - 5 && my <= bottomEdge || holding == HoldState.BOTTOM;
    } 
}
