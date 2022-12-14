package wintersteve25.rpgutils.common.systems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wintersteve25.rpgutils.RPGUtils;
import wintersteve25.rpgutils.client.ui.dialogues.DialogueUI;
import wintersteve25.rpgutils.common.data.loaded.dialogue.dialogue.Dialogue;
import wintersteve25.rpgutils.common.data.loaded.dialogue.dialogue.DialogueManager;
import wintersteve25.rpgutils.common.data.loaded.dialogue.dialogue_pool.DialoguePoolManager;
import wintersteve25.rpgutils.common.data.loaded.dialogue.dialogue_pool.DialogueRule;
import wintersteve25.rpgutils.common.network.ModNetworking;
import wintersteve25.rpgutils.common.network.PacketPlayDialogue;
import wintersteve25.rpgutils.common.utils.RandomCollection;

import java.util.List;

public class DialogueSystem {
    @OnlyIn(Dist.CLIENT)
    public static void play(PlayerEntity player, ResourceLocation dialoguePool) {
        RPGUtils.LOGGER.info("Attempting to play dialogue pool: {}", dialoguePool);
        
        List<DialogueRule> pool = DialoguePoolManager.INSTANCE.getPools().get(dialoguePool);
        
        if (pool == null) {
            RPGUtils.LOGGER.error("Given pool name is not found");
            return;
        }

        if (pool.isEmpty()) {
            RPGUtils.LOGGER.error("No dialogue found in pool");
            return;
        }
        
        RandomCollection<DialogueRule> rules = new RandomCollection<>();
        for (DialogueRule rule : pool) {
            if (DialogueManager.INSTANCE.getDialogues().get(rule.getDialogue()).isValid()) {
                rules.add(rule.getWeight(), rule);
            }
        }
        
        DialogueRule randomRule = rules.next();
        ResourceLocation dialogueRL = randomRule.getDialogue();
        Dialogue dialogue = DialogueManager.INSTANCE.getDialogues().get(dialogueRL); 
        
        if (dialogue == null) {
            RPGUtils.LOGGER.error("Specified dialogue: {} was not found", dialogueRL);
            return;
        }
        
        DialogueUI.show(player, dialogue, randomRule.isInterruptable());
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void play(ResourceLocation dialoguePool) {
        play(Minecraft.getInstance().player, dialoguePool);
    }
    
    public static void playFromServer(ResourceLocation dialoguePool, ServerPlayerEntity player) {
        ModNetworking.sendToClient(new PacketPlayDialogue(dialoguePool, player), player);
    }
}
