package wintersteve25.rpgutils.client.ui_creator.serialization;

import com.google.common.collect.ImmutableMap;
import dev.ftb.mods.ftblibrary.ui.Widget;
import wintersteve25.rpgutils.RPGUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class UIProfile {

    private Map<String, Widget> children;
    private boolean finalized;
    
    public UIProfile() {
        children = new HashMap<>();
    }
    
    @Nullable
    public Widget getChild(String id) {
        return children.get(id);
    }
    
    public void createOrReplaceWidget(String id, Widget widget) {
        children.remove(id);
        children.put(id, widget);
    }

    public void removeWidget(String id) {
        children.remove(id);
    }
    
    public void finalizeUI() {
        finalized = true;
        children = ImmutableMap.copyOf(children);
    }
    
    public Map<String, Widget> getChildren() {
        if (!finalized) {
            RPGUtils.LOGGER.warn("Using an un-finalized UI Profile");
        }
        return children;
    }
}