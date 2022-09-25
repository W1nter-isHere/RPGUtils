package wintersteve25.rpgutils.client.ui_creator.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.*;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.client.resources.I18n;
import wintersteve25.rpgutils.client.ui_creator.widget.type.WidgetType;

public class SelectWidgetPanel extends BaseScreen {

    private final Panel panelButtons;
    private final PanelScrollBar scrollBar;
    private final TextBox searchBox;
    
    public WidgetType typeSelected;

    public SelectWidgetPanel(Panel panel) {
        setSize(200, 120);
        
        panelButtons = new Panel(this) {
            @Override
            public void add(Widget widget) {
                if (searchBox.getText().isEmpty() || getFilterText(widget).contains(searchBox.getText().toLowerCase())) {
                    super.add(widget);
                }
            }

            @Override
            public void addWidgets() {
                addButtons(this);
            }

            @Override
            public void alignWidgets() {
                setY(23);
                int prevWidth = width;

                if (widgets.isEmpty()) {
                    setWidth(160);
                } else {
                    setWidth(160);

                    for (Widget w : widgets) {
                        setWidth(Math.max(width, w.width));
                    }
                }

                if (width > SelectWidgetPanel.this.width - 40) {
                    width = SelectWidgetPanel.this.width - 40;
                }
                
                setWidth(Math.max(width, prevWidth));

                for (Widget w : widgets) {
                    w.setX(0);
                    w.setWidth(width);
                }

                setHeight(90);

                scrollBar.setPosAndSize(posX + width + 6, posY - 1, 16, 90);
                scrollBar.setMaxValue(align(WidgetLayout.VERTICAL));

                searchBox.setPosAndSize(8, 6, getGui().width - 16, 12);
            }

            @Override
            public void drawBackground(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
                theme.drawPanelBackground(matrixStack, x, y, w, h);
            }
        };

        panelButtons.setPosAndSize(9, 9, 0, 146);

        scrollBar = new PanelScrollBar(this, panelButtons);
        scrollBar.setCanAlwaysScroll(true);
        scrollBar.setScrollStep(20);

        searchBox = new TextBox(this) {
            @Override
            public void onTextChanged() {
                panelButtons.refreshWidgets();
            }
        };

        searchBox.ghostText = I18n.get("gui.search_box");

        this.parent = panel;
    }
    
    @Override
    public void drawBackground(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
        super.drawBackground(matrixStack, theme, x, y, w, h);
    }

    @Override
    public int getX() {
        return this.parent.getX() + this.posX;
    }

    @Override
    public int getY() {
        return this.parent.getY() + this.posY;
    }

    public String getFilterText(Widget widget) {
        return widget.getTitle().getString().toLowerCase();
    }

    @Override
    public void addWidgets() {
        add(panelButtons);
        add(scrollBar);
        add(searchBox);
    }

    @Override
    public void alignWidgets() {
        panelButtons.alignWidgets();
    }

    public void addButtons(Panel panel) {
        for (WidgetType widgetType : WidgetType.values()) {
            panel.add(new SimpleTextButton(panel, widgetType.getDisplayName(), Icon.EMPTY) {
                @Override
                public void onClicked(MouseButton mouseButton) {
                    if (mouseButton.isLeft()) {
                        playClickSound();
                        typeSelected = widgetType;
                    }
                }

                @Override
                public dev.ftb.mods.ftblibrary.ui.WidgetType getWidgetType() {
                    return typeSelected == widgetType ? dev.ftb.mods.ftblibrary.ui.WidgetType.MOUSE_OVER : super.getWidgetType();
                }
            });
        }
    }
}
