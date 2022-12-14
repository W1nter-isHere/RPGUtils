package wintersteve25.rpgutils.client.ui.dialogues.selections.sound_registry;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.rpgutils.client.ui.dialogues.components.selection.AbstractSelectionUI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SelectSound extends AbstractSelectionUI<SoundOption> {
    public SelectSound(boolean allowMultiple, Consumer<List<SoundOption>> onSubmit) {
        super(allowMultiple, onSubmit);
    }

    @Override
    protected void populateOptions(List<SoundOption> list) {
        List<SoundEvent> sounds = new ArrayList<>(ForgeRegistries.SOUND_EVENTS.getValues());

        list.add(new SoundOption(this.x + 10, this.y + 40, this, 0, null));
        
        for (int i = 0; i < sounds.size(); i++) {
            list.add(new SoundOption(this.x + 10, this.y + 40 + (i + 1) * 12, this, i + 1, sounds.get(i)));
        }
    }

    @Override
    protected SoundOption copyFrom(SoundOption copyFrom) {
        return new SoundOption(copyFrom);
    }

    @Override
    protected boolean isSameType(IGuiEventListener listener) {
        return listener instanceof SoundOption;
    }
}