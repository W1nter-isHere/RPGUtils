package wintersteve25.rpgutils.client.ui.dialogue_creator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.rpgutils.RPGUtils;
import wintersteve25.rpgutils.client.ui.components.BaseUI;
import wintersteve25.rpgutils.common.utils.JsonUtilities;
import wintersteve25.rpgutils.common.utils.RLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DialogueEditorUI extends BaseUI {

    private static final ResourceLocation BG = new ResourceLocation(RPGUtils.MOD_ID, "textures/gui/dialogue_editor.png");
    private static final TranslationTextComponent TITLE = RLHelper.dialogueEditorComponent("title");
    private static final TranslationTextComponent SAVE = RLHelper.dialogueEditorComponent("save");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 240;
    
    private final List<DialogueActionGui> actions;

    protected DialogueEditorUI() {
        super(BG, WIDTH, HEIGHT);
        this.actions = new ArrayList<>();
    }

    @Override
    protected void init() {
        super.init();

        int rightX = this.x + texWidth;

        Button addNewButton = new Button(rightX - 95, this.y + 5, 20, 20, new StringTextComponent("+"), btn -> {
            DialogueActionGui actionGui = new DialogueActionGui(actions.size());
            actionGui.init(this.x + 15, this.y, this);
            actions.add(actionGui);
        });

        Button removeButton = new Button(rightX - 70, this.y + 5, 20, 20, new StringTextComponent("-"), btn -> {
            List<DialogueActionGui> matches = actions.stream().filter(DialogueActionGui::isSelected).collect(Collectors.toList());

            for (DialogueActionGui widget : matches) {
                widget.remove(this);
                actions.remove(widget);
            }

            // update the remaining ones' index
            for (int i = 0; i < actions.size(); i++) {
                DialogueActionGui actionGui = actions.get(i);
                // does not require update
                if (actionGui.getIndex() == i) continue;
                actionGui.setIndex(i);
                actionGui.init(this.x + 15, this.y, this);
            }
        });

        Button saveButton = new Button(rightX - 45, this.y + 5, 40, 20, SAVE, btn -> {
            JsonArray lines = new JsonArray();

            for (DialogueActionGui actionGui : actions) {
                JsonObject line = new JsonObject();
                if (actionGui.getSelectedEntity() != null) line.addProperty("speaker", actionGui.getSelectedEntity().toString());
                line.add("action", actionGui.getActionTypeGui().save().toJson());
                lines.add(line);
            }

            JsonUtilities.saveDialogue(lines);
        });

        for (DialogueActionGui actionGui : actions) {
            actionGui.init(this.x + 15, this.y, this);
        }

        addButton(addNewButton);
        addButton(removeButton);
        addButton(saveButton);
    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);

        pMatrixStack.pushPose();

        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bind(bg);

        // MatrixStack pMatrixStack, int pX, int pY, int pBlitOffset, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureHeight, int pTextureWidth
        blit(pMatrixStack, this.x, this.y, getBlitOffset(), 0, 0, texWidth, texHeight, 512, 512);
        drawString(pMatrixStack, minecraft.font, TITLE, this.x + 15, this.y + 10, TextFormatting.WHITE.getColor());

        pMatrixStack.popPose();

        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
    }

    @Override
    public void tick() {
        for (DialogueActionGui actionGui : actions) {
            actionGui.tick();
        }
    }
    
    public static void open() {
        Minecraft.getInstance().setScreen(null);
        Minecraft.getInstance().setScreen(new DialogueEditorUI());
    }
}