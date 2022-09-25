package wintersteve25.rpgutils.client.ui_creator;

import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;

public interface IUIProfileUI {
    void onButtonClicked(Button button, MouseButton mouseButton, String customId);
}
