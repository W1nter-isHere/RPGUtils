package wintersteve25.rpgutils.client.ui_creator.serialization;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import wintersteve25.rpgutils.RPGUtils;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.ButtonWidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.PanelWidgetInstance;
import wintersteve25.rpgutils.client.ui_creator.serialization.template.WidgetInstance;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class UIProfile {

    private static final Type type;
    private static final Gson gson;
    private static final RuntimeTypeAdapterFactory<WidgetInstance> typeAdapterFactory;

    static {
        type = new TypeToken<Map<String, WidgetInstance>>(){}.getType();
        
        typeAdapterFactory = RuntimeTypeAdapterFactory.of(WidgetInstance.class)
                .registerSubtype(ButtonWidgetInstance.class)
                .registerSubtype(PanelWidgetInstance.class);
        
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapterFactory(typeAdapterFactory)
                .create();
    }

    private Map<String, WidgetInstance> children;
    private boolean finalized;
    
    public UIProfile() {
        children = new HashMap<>();
    }

    private UIProfile(Map<String, WidgetInstance> children, boolean finalized) {
        this.children = children;
        this.finalized = finalized;
    }

    @Nullable
    public WidgetInstance getChild(String id) {
        return children.get(id);
    }
    
    public void modifyChild(String id, boolean updateEditor, Consumer<WidgetInstance> consumer) {
        if (!children.containsKey(id)) return;
        WidgetInstance template = children.get(id);
        consumer.accept(template);

        if (updateEditor) {
            onWidgetRemoved(id);
            onWidgetAdded(id, template);
        }
    }
    
    public void createOrReplaceWidget(WidgetInstance widget) {
        String id = widget.getCustomId();
        
        if (children.containsKey(id)) {
            children.remove(id);
            onWidgetRemoved(id);
        }

        children.put(id, widget);
        onWidgetAdded(id, widget);
    }
    
    public void removeWidget(String id) {
        if (!children.containsKey(id)) return;
        children.remove(id);
        onWidgetRemoved(id);
    }
    
    public void onWidgetAdded(String id, WidgetInstance widget) {
    }
    
    public void onWidgetRemoved(String id) {
    }
    
    public void finalizeUI() {
        finalized = true;
        children = ImmutableMap.copyOf(children);
    }
    
    public Map<String, WidgetInstance> getChildren() {
        if (!finalized) {
            RPGUtils.LOGGER.warn("Using an un-finalized UI Profile");
        }
        return children;
    }
    
    public JsonElement saveToJson() {
        return gson.toJsonTree(children, type);
    }
    
    public static UIProfile loadFromJson(JsonElement jsonElement) {
        return new UIProfile(gson.fromJson(jsonElement, type), true);
    }
}