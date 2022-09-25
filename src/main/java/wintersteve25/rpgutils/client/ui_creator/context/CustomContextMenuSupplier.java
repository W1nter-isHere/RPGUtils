package wintersteve25.rpgutils.client.ui_creator.context;

import dev.ftb.mods.ftblibrary.ui.Panel;
import wintersteve25.rpgutils.client.ui_creator.EditableWidget;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;

@FunctionalInterface
public interface CustomContextMenuSupplier {
    CustomContextMenu create(Panel parent, EditableWidget widget);
}
