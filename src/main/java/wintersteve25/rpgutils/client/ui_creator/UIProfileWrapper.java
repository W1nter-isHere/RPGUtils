package wintersteve25.rpgutils.client.ui_creator;

import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Widget;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;

public class UIProfileWrapper extends BaseScreen {
    
    private final UIProfile uiProfile;
    
    public UIProfileWrapper(UIProfile uiProfile) {
        this.uiProfile = uiProfile;
    }
    
    @Override
    public void addWidgets() {
        for (Widget widget : uiProfile.getChildren().values()) {
            add(widget);
        }
    }
    
    protected void onButtonPressed(String widgetId) {
    }
}
