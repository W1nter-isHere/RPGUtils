package wintersteve25.rpgutils.common.data.loaded.dialogue.dialogue.predicate;

import com.google.gson.JsonObject;

public abstract class DialoguePredicate {

    /**
     * @param jsonObject The JsonObject representing the "predicate" field in the dialogue's JSON file
     */
    protected DialoguePredicate(JsonObject jsonObject) {}

    public abstract boolean test();
    
    public abstract JsonObject toJson();

    /**
     * @return required data read by PredicateGui when initiating
     */
    public abstract Object[] data();
    
    public abstract int guiIndex();
    
    /**
     * Currently, each predicate type must have its own block in the switch statement - could be improved in the future.
     * @param name The name associated with the desired DialoguePredicate (in the JSON files)
     * @param jsonObject See constructor
     * @return The DialoguePredicate that corresponds to the "name" parameter
     */
    public static DialoguePredicate create(String name, JsonObject jsonObject) {
        DialoguePredicate predicate = null;
        switch (name) {
            case "localPlayerHealth":
                predicate = new LocalPlayerHealthPredicate(jsonObject);
                break;
            case "and":
                predicate = new AndPredicate(jsonObject);
                break;
            case "or":
                predicate = new OrPredicate(jsonObject);
                break;
        }
        return predicate;
    }
    
    public static DialoguePredicate create(JsonObject jsonObject) {
        return create(jsonObject.get("name").getAsString(), jsonObject);
    }
}
