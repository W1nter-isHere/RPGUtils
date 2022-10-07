package wintersteve25.rpgutils.client.ui_creator;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.util.ResourceLocation;
import wintersteve25.rpgutils.RPGUtils;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.WidgetInstance;
import wintersteve25.rpgutils.common.data.loaded.ui.UIManager;

public class UIProfileWrapper extends BaseScreen implements IUIProfileUI {
    
    private final UIProfile uiProfile;
    
    public UIProfileWrapper(UIProfile uiProfile) {
        this.uiProfile = uiProfile;
    }
    
    @Override
    public void addWidgets() {
        for (WidgetInstance widget : uiProfile.getChildren().values()) {
            add(widget.create(this));
        }
    }

    @Override
    public void drawBackground(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
    }

    @Override
    public void onButtonClicked(Button widget, MouseButton mouseButton, String customId) {
        System.out.println(customId);
    }

    @Override
    public boolean onInit() {
        return setFullscreen();
    }
    
    public static void open(String id) {
        UIProfile profile = UIManager.INSTANCE.getUIs().get(new ResourceLocation(RPGUtils.MOD_ID, id));
        if (profile == null) return;
        new UIProfileWrapper(profile).openGui();
    }
}