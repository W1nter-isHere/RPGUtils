package wintersteve25.rpgutils.common.data.loaded.quest.objectives.predicates;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class BlockPredicate {
    public static final BlockPredicate ANY = new BlockPredicate(null, null, null, StatePropertiesPredicate.ANY, NBTPredicate.ANY);
    
    @Nullable
    private final ITag<Block> tag;
    @Nullable
    private final Block block;
    @Nullable
    private final BlockPos pos;
    private final StatePropertiesPredicate properties;
    private final NBTPredicate nbt;

    public BlockPredicate(@Nullable ITag<Block> pTag, @Nullable Block pBlock, @Nullable BlockPos pos, StatePropertiesPredicate pProperties, NBTPredicate pNbt) {
        this.tag = pTag;
        this.block = pBlock;
        this.pos = pos;
        this.properties = pProperties;
        this.nbt = pNbt;
    }

    public boolean matches(ServerWorld pLevel, BlockPos pPos) {
        if (this == ANY) {
            return true;
        } else if (!pLevel.isLoaded(pPos)) {
            return false;
        } else {
            BlockState blockstate = pLevel.getBlockState(pPos);
            Block block = blockstate.getBlock();
            if (this.pos != null && !pos.equals(pPos)) {
                return false;
            } else if (this.tag != null && !this.tag.contains(block)) {
                return false;
            } else if (this.block != null && block != this.block) {
                return false;
            } else if (!this.properties.matches(blockstate)) {
                return false;
            } else {
                if (this.nbt != NBTPredicate.ANY) {
                    TileEntity tileentity = pLevel.getBlockEntity(pPos);
                    if (tileentity == null || !this.nbt.matches(tileentity.save(new CompoundNBT()))) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static BlockPredicate fromJson(@Nullable JsonElement pJson) {
        if (pJson != null && !pJson.isJsonNull()) {
            JsonObject jsonobject = JSONUtils.convertToJsonObject(pJson, "block");
            NBTPredicate nbtpredicate = NBTPredicate.fromJson(jsonobject.get("nbt"));
            Block block = null;
            if (jsonobject.has("block")) {
                ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getAsString(jsonobject, "block"));
                block = Registry.BLOCK.get(resourcelocation);
            }

            ITag<Block> itag = null;
            if (jsonobject.has("tag")) {
                ResourceLocation resourcelocation1 = new ResourceLocation(JSONUtils.getAsString(jsonobject, "tag"));
                itag = TagCollectionManager.getInstance().getBlocks().getTag(resourcelocation1);
                if (itag == null) {
                    throw new JsonSyntaxException("Unknown block tag '" + resourcelocation1 + "'");
                }
            }

            BlockPos pos = null;
            if (jsonobject.has("pos")) {
                pos = BlockPos.CODEC.parse(JsonOps.INSTANCE, jsonobject.get("pos")).result().get();
            }

            StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.fromJson(jsonobject.get("state"));

            return new BlockPredicate(itag, block, pos, statepropertiespredicate, nbtpredicate);
        } else {
            return ANY;
        }
    }

    public JsonElement serializeToJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject jsonobject = new JsonObject();
            if (this.block != null) {
                jsonobject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
            }

            if (this.tag != null) {
                jsonobject.addProperty("tag", TagCollectionManager.getInstance().getBlocks().getIdOrThrow(this.tag).toString());
            }
            
            if (this.pos != null) {
                jsonobject.add("pos", BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos).result().get());
            }

            jsonobject.add("nbt", this.nbt.serializeToJson());
            jsonobject.add("state", this.properties.serializeToJson());
            
            return jsonobject;
        }
    }
}