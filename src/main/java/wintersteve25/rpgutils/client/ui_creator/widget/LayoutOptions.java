package wintersteve25.rpgutils.client.ui_creator.widget;

import dev.ftb.mods.ftblibrary.ui.WidgetLayout;
import wintersteve25.rpgutils.client.ui.components.CenterLayout;

public enum LayoutOptions {
    CENTER {
        @Override
        public WidgetLayout get() {
            return new CenterLayout(0);
        }
    },
    VERTICAL {
        @Override
        public WidgetLayout get() {
            return WidgetLayout.VERTICAL;
        }
    },
    HORIZONTAL {
        @Override
        public WidgetLayout get() {
            return WidgetLayout.HORIZONTAL;
        }
    };
    
    public abstract WidgetLayout get();
}
