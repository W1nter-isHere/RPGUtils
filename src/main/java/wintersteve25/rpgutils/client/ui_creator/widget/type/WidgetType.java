package wintersteve25.rpgutils.client.ui_creator.widget.type;

import dev.ftb.mods.ftblibrary.icon.Icon;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import wintersteve25.rpgutils.client.ui_creator.context.ContextPanelBuilder;
import wintersteve25.rpgutils.client.ui_creator.context.CustomContextMenuSupplier;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.ButtonWidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.PanelWidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.WidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.widget.widgets.ButtonWidget;
import wintersteve25.rpgutils.client.ui_creator.widget.widgets.PanelWidget;

import javax.annotation.Nullable;
import java.util.ArrayList;

public enum WidgetType implements IWidgetType {
    SIMPLE_TEXT_BUTTON {
        @Override
        public ITextComponent getDisplayName() {
            return new StringTextComponent("Simple Text Button");
        }

        @Override
        public WidgetSupplier getWidgetConstructor() {
            return (panel, customId) -> new ButtonWidget(panel, new StringTextComponent("Simple Text Button"), Icon.EMPTY, customId);
        }

        @Nullable
        @Override
        public CustomContextMenuSupplier getEditContextPanel() {
            return new ContextPanelBuilder()
                    .renamable()
                    .movable()
                    .resizable()
                    .removable()
                    .build();
        }

        @Override
        public int getDefaultWidth() {
            return 100;
        }

        @Override
        public int getDefaultHeight() {
            return 20;
        }

        @Override
        public WidgetInstance createInstance(String customId, int x, int y) {
            return new ButtonWidgetInstance(this, customId, x, y, getDefaultWidth(), getDefaultHeight(), getDisplayName().getContents());
        }
    },
    
    PANEL {
        @Override
        public ITextComponent getDisplayName() {
            return new StringTextComponent("Panel");
        }

        @Override
        public WidgetSupplier getWidgetConstructor() {
            return PanelWidget::new;
        }

        @Nullable
        @Override
        public CustomContextMenuSupplier getEditContextPanel() {
            return new ContextPanelBuilder()
                    .addChild()
                    .movable()
                    .resizable()
                    .removable()
                    .build();
        }

        @Override
        public int getDefaultWidth() {
            return 100;
        }

        @Override
        public int getDefaultHeight() {
            return 160;
        }

        @Override
        public WidgetInstance createInstance(String customId, int x, int y) {
            return new PanelWidgetInstance(this, new ArrayList<>(), customId, x, y, getDefaultWidth(), getDefaultHeight());
        }
    }
}