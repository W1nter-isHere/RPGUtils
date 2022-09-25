package wintersteve25.rpgutils.client.ui_creator.widget.type;

import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Widget;

@FunctionalInterface
public interface WidgetSupplier {
    Widget create(Panel parent, String customId);
}
