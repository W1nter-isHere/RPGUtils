package wintersteve25.rpgutils.client.ui_creator;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.util.ResourceLocation;
import wintersteve25.rpgutils.RPGUtils;
import wintersteve25.rpgutils.client.ui_creator.context.ClickBlankContextMenu;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.WidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.widget.type.WidgetType;
import wintersteve25.rpgutils.common.data.loaded.ui.UIManager;
import wintersteve25.rpgutils.common.utils.JsonUtilities;

import java.util.HashMap;
import java.util.Map;

public class UICreatorUI extends BaseScreen implements IUIProfileUI {
    
    public static UICreatorUI activeInstance;
    
    private final ResourceLocation name;
    private final UIProfile uiProfile;
    private final Map<String, Widget> widgets;
    
    private boolean initialized;
    
    public UICreatorUI(String name) {
        this.name = new ResourceLocation(RPGUtils.MOD_ID, name);
        
        UIProfile profile = UIManager.INSTANCE.getUIs().get(this.name);
        
        uiProfile = profile == null ? new UIProfile() {
            @Override
            public void onWidgetAdded(String id, WidgetInstance widget) {
                UICreatorUI.this.onWidgetAdded(id, widget);
            }

            @Override
            public void onWidgetRemoved(String id) {
                UICreatorUI.this.onWidgetRemoved(id);
            }
        } : profile;
        widgets = new HashMap<>();
    }
    
    @Override
    public void addWidgets() {
        if (!initialized) {
            for (WidgetInstance widget : uiProfile.getChildren().values()) {
                widgets.put(widget.getCustomId(), widget.createEditable(this, uiProfile));
            }
        }
        
        for (Widget widget : widgets.values()) {
            add(widget);
        }
    }

    public void createWidget(String id, WidgetType widgetType, int x, int y) {
        WidgetInstance widgetInstance = widgetType.createInstance(id, x, y);
        uiProfile.createOrReplaceWidget(widgetInstance);
    }
    
    private void onWidgetAdded(String id, WidgetInstance widget) {
        widgets.put(id, widget.createEditable(this, uiProfile));
        refreshWidgets();
    }
    
    private void onWidgetRemoved(String id) {
        widgets.remove(id);
        refreshWidgets();
    }

    @Override
    public boolean mousePressed(MouseButton button) {
        if (button.isRight()) {
            if (!isMouseOverAnyWidget()) {
                int px = 0;
                int py = 0;
                
                int x = this.getX();
                int y = this.getY();
                if (this.contextMenu == null) {
                    px = this.getMouseX() - x;
                    py = this.getMouseY() - y;
                }
                
                openContextMenu(new ClickBlankContextMenu(this, px, py));
                return true;
            }
        } else if (button.isLeft()) {
            if (!isMouseOverAnyWidget()) {
                closeContextMenu();
            }
        }
        
        return super.mousePressed(button);
    }
    
    @Override
    public void drawBackground(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
    }

    @Override
    public void onButtonClicked(Button widget, MouseButton mouseButton, String customId) {
        System.out.println(customId);
    }

    @Override
    public void onClosed() {
        super.onClosed();
        JsonUtilities.saveUI(name, uiProfile.saveToJson());
    }

    public UIProfile getUiProfile() {
        return uiProfile;
    }

    public static void open(String name) {
        activeInstance = new UICreatorUI(name);
        activeInstance.openGui();
    }
}    
