package wintersteve25.rpgutils.common.data.loaded.ui;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.util.ResourceLocation;
import wintersteve25.rpgutils.RPGUtils;
import wintersteve25.rpgutils.client.ui_creator.serialization.UIProfile;
import wintersteve25.rpgutils.common.data.loaded.JsonDataLoader;

import java.util.HashMap;
import java.util.Map;

public class UIManager extends JsonDataLoader {
    public static final UIManager INSTANCE = new UIManager();

    private final Map<ResourceLocation, UIProfile> ui = new HashMap<>();

    public UIManager() {
        super("ui");
    }

    public Map<ResourceLocation, UIProfile> getUIs() {
        return ui;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject) {
        RPGUtils.LOGGER.info("Loading UIs");

        ui.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            if (resourcelocation.getPath().startsWith("_"))
                continue; //Forge: filter anything beginning with "_" as it's used for metadata.
            try {
                ui.put(resourcelocation, UIProfile.loadFromJson(entry.getValue()));
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                RPGUtils.LOGGER.error("Parsing error loading ui {}", resourcelocation, jsonparseexception);
            }
        }
    }
}
