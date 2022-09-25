package wintersteve25.rpgutils.client.ui_creator.widget;

import dev.ftb.mods.ftblibrary.ui.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import wintersteve25.rpgutils.client.ui.components.CenterLayout;
import wintersteve25.rpgutils.client.ui.components.SubmitOrCancel;
import wintersteve25.rpgutils.client.ui_creator.widget.type.WidgetType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NewWidgetScreen extends BaseScreen {
    
    private final TextField title;
    private final TextBox id;
    private final SelectWidgetPanel selectWidgetPanel;
    private final SubmitOrCancel submitOrCancel;

    public NewWidgetScreen(BiConsumer<String, WidgetType> onSubmit) {
        setSize(260, 230);

        this.title = new TextField(this);
        this.title.setText(new StringTextComponent("New Widget"));
        
        this.id = new TextBox(this);
        this.id.setSize(200, 20);
        this.id.ghostText = "Custom Id";
        
        this.selectWidgetPanel = new SelectWidgetPanel(this);

        this.submitOrCancel = new SubmitOrCancel(this, () -> {
            if (selectWidgetPanel.typeSelected == null) return;
            if (id.getText().isEmpty()) return;
            onSubmit.accept(id.getText(), selectWidgetPanel.typeSelected);
        }, () -> {
            Minecraft.getInstance().setScreen(getPrevScreen());
        });
        this.submitOrCancel.setSize(200, 20);
    }

    @Override
    public void addWidgets() {
        add(title);
        add(id);
        add(selectWidgetPanel);
        add(submitOrCancel);
    }

    @Override
    public void alignWidgets() {
        selectWidgetPanel.initGui();
        align(new CenterLayout(10));
    }
}
