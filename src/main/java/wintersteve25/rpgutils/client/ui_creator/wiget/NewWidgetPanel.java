package wintersteve25.rpgutils.client.ui_creator.wiget;

import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.TextBox;

public class NewWidgetPanel extends Panel {
    
    private final TextBox id;
    private final 
    
    public NewWidgetPanel(Panel panel) {
        super(panel);
        this.id = new TextBox(this);
        
    }

    @Override
    public void addWidgets() {
        
    }

    @Override
    public void alignWidgets() {

    }
}
