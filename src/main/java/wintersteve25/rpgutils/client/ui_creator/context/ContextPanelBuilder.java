package wintersteve25.rpgutils.client.ui_creator.context;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import wintersteve25.rpgutils.client.ui.quests.creator.TextInputPrompt;
import wintersteve25.rpgutils.client.ui_creator.UICreatorUI;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.ButtonWidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.PanelWidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.WidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.widget.NewWidgetScreen;
import wintersteve25.rpgutils.client.ui_creator.widget.widgets.PanelWidget;

import java.util.ArrayList;
import java.util.List;

public class ContextPanelBuilder {
    private final List<ContextMenuItem> items;

    public ContextPanelBuilder() {
        items = new ArrayList<>();
    }

    public ContextPanelBuilder renamable() {
        items.add(new CustomContextMenuItem(new StringTextComponent("Change Title"), Icons.INFO, editableWidget -> {
            TextInputPrompt textInputPrompt = new TextInputPrompt(null, new StringTextComponent("Change Title"), "New Title",
                    (prmpt, mouse) -> {
                        if (!mouse.isLeft()) {
                            return;
                        }

                        String text = prmpt.enterText.getText();
                        if (text.isEmpty()) {
                            return;
                        }
                        
                        editableWidget.getUiProfile().modifyChild(editableWidget.getCustomId(), true, template -> {
                            if (template instanceof ButtonWidgetInstance) {
                                ((ButtonWidgetInstance) template).setTitle(text);
                            }

                            prmpt.playClickSound();
                            prmpt.disable();
                            Minecraft.getInstance().setScreen(prmpt.getPrevScreen());
                        });
                    },
                    (prmpt, mouse) -> {
                        if (!mouse.isLeft()) {
                            return;
                        }

                        prmpt.playClickSound();
                        prmpt.disable();
                        Minecraft.getInstance().setScreen(prmpt.getPrevScreen());
                    });
            textInputPrompt.enable();
            textInputPrompt.openGui();
        }, null));
        return this;
    }

    public ContextPanelBuilder removable() {
        items.add(new CustomContextMenuItem(new StringTextComponent("Remove"), Icons.REMOVE, editableWidget -> editableWidget.getUiProfile().removeWidget(editableWidget.getCustomId()), null));
        return this;
    }
    
    public ContextPanelBuilder movable() {
        items.add(new CustomContextMenuItem(new StringTextComponent("Move"), Icons.BARRIER, editableWidget -> {
            editableWidget.setMoving(!editableWidget.isMoving());
            editableWidget.setResizing(false);
            editableWidget.setPreviewing(false);
        }, (editableWidget, matrixStack, theme, x, y, w, h) -> {
            Icon icon = editableWidget.isMoving() ? Icons.CHECK : Icons.BARRIER;
            icon.draw(matrixStack, x, y, w, h);
        }));
        return this;
    }
    
    public ContextPanelBuilder resizable() {
        items.add(new CustomContextMenuItem(new StringTextComponent("Resize"), Icons.BARRIER, editableWidget -> {
            editableWidget.setResizing(!editableWidget.isResizing());
            editableWidget.setMoving(false);
            editableWidget.setPreviewing(false);
        }, (editableWidget, matrixStack, theme, x, y, w, h) -> {
            Icon icon = editableWidget.isResizing() ? Icons.CHECK : Icons.BARRIER;
            icon.draw(matrixStack, x, y, w, h);
        }));
        return this;
    }
    
    public ContextPanelBuilder addChild() {
        items.add(new CustomContextMenuItem(new StringTextComponent("Add Child"), Icons.ADD, editableWidget -> {
            new NewWidgetScreen((customId, type) -> {
                WidgetInstance widgetInstance = type.createInstance(customId, 0, 0);
                editableWidget.getUiProfile().modifyChild(editableWidget.getCustomId(), true, instance -> {
                    if (instance instanceof PanelWidgetInstance) {
                        ((PanelWidgetInstance) instance).getChildren().add(widgetInstance);
                    }
                    UICreatorUI.activeInstance.playClickSound();
                    UICreatorUI.activeInstance.openGui();
                });
            }).openGui();
        }, null));
        return this;
    }
    
    public CustomContextMenuSupplier build() {
        return (parent, editableWidget) -> new CustomContextMenu(parent, editableWidget, items);
    }
}
