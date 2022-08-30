package wintersteve25.rpgutils.common.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.rpgutils.RPGUtils;
import wintersteve25.rpgutils.client.animation.IAnimatedEntity;
import wintersteve25.rpgutils.common.data.loaded.npc.NPCDatumType;
import wintersteve25.rpgutils.common.data.loaded.npc.NPCTypeLoader;
import wintersteve25.rpgutils.common.data.loaded.npc.goal.ModGoals;
import wintersteve25.rpgutils.common.network.ModNetworking;
import wintersteve25.rpgutils.common.network.PacketSetType;
import wintersteve25.rpgutils.common.registry.ModMemoryModuleTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class NPCEntity extends MobEntity implements IAnimatedEntity<NPCEntity> {

    private final AnimationFactory factory = new AnimationFactory(this);
    /**
     * Use caution when accessing directly - invoking getNPCType() instead will ensure initialisation from NBT if necessary.
     */
    private NPCType npcType = null;

    public NPCEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public static NPCEntity create(EntityType<? extends MobEntity> entityType, World world, String type) {
        NPCEntity entity = new NPCEntity(entityType, world);
        entity.setNPCType(type);
        return entity;
    }

    public static AttributeModifierMap createAttributes() {
        return NPCTypeLoader.INSTANCE.createDefaultAttributes();
    }

    @Override
    protected void registerGoals() {
        NPCType type = getNPCType();
        if (type != null) {
            Map<ModGoals.GoalConstructor, Integer> goalWeights = (Map<ModGoals.GoalConstructor, Integer>) type.getDatum(NPCDatumType.GOAL_WEIGHTS);
            for (Map.Entry<ModGoals.GoalConstructor, Integer> goal : goalWeights.entrySet()) {
                this.goalSelector.addGoal(goal.getValue(), goal.getKey().apply(this));
            }
        }
    }

    @Override
    protected Brain.BrainCodec<NPCEntity> brainProvider() {
        return Brain.provider(ImmutableList.of(ModMemoryModuleTypes.MOVEMENT_TARGET.get()), ImmutableList.of(SensorType.NEAREST_PLAYERS));
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        Brain<NPCEntity> brain = this.brainProvider().makeBrain(dynamic);
        brain.setMemory(ModMemoryModuleTypes.MOVEMENT_TARGET.get(), new BlockPos(0, 61, 0));
        return brain;
    }

    /**
     * Sets the NPC's type. Should only be used once immediately after construction - otherwise duplicate goals will be added. Updates persistent data, attributes and goals.
     * @param type The name (as in NPCTypeLoader.typeMap) of the new NPC type
     */
    private void setNPCType(String type) {
        this.npcType = NPCTypeLoader.INSTANCE.getType(type);
        this.getPersistentData().putString(getTypeKey(), (String) this.npcType.getDatum(NPCDatumType.NAME));
        NPCTypeLoader.INSTANCE.setAttributes(this, (String) this.npcType.getDatum(NPCDatumType.NAME));
        this.registerGoals();
    }

    public NPCType getNPCType() {
        if (npcType == null) {
            String type = this.getPersistentData().getString(getTypeKey());
            if (!type.equals("")) {
                this.setNPCType(type);
            } else npcType = null;
        }
        return npcType;
    }

    /**
     * Sets the NPC's type. Should be used only by the client on receival of PacketSetType. Does not update persistent data or attributes.
     * @param type The new NPC type
     */
    public void setNPCType(NPCType type) {
        this.npcType = type;
    }

    public void updateClients() {
        for (ServerPlayerEntity player : getServer().getLevel(this.level.dimension()).players()) {
            updateClient(player);
        }
    }

    public void updateClient(ServerPlayerEntity client) {
        PacketSetType packet = new PacketSetType(this.getId(), getNPCType());
        ModNetworking.sendToClient(packet, client);
    }

    public String getTexturePath() {
        NPCType type = getNPCType();
        if (type == null) {
            return NPCType.DEFAULT_TEXTURE;
        }
        return (String) type.getDatum(NPCDatumType.TEXTURE);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.npc.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        RPGUtils.LOGGER.info(getPersistentData().get(getTypeKey()));
        return SoundEvents.VILLAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    @Override
    public void setAnimation(String controller, String animation) {}

    @Override
    public NPCEntity getSelf() {
        return this;
    }

    private static String getTypeKey() {
        return RPGUtils.MOD_ID + "_name";
    }

    @Override
    public ITextComponent getName() {
        NPCType type = getNPCType();
        if (type == null) {
            return new TranslationTextComponent("entity." + RPGUtils.MOD_ID + ".npc.npc");
        }
        return new TranslationTextComponent("entity." + RPGUtils.MOD_ID + ".npc." + type.getDatum(NPCDatumType.NAME));
    }
}
