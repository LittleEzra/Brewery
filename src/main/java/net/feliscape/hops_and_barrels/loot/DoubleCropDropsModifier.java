package net.feliscape.hops_and_barrels.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.feliscape.hops_and_barrels.effect.ModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DoubleCropDropsModifier extends LootModifier {
    public static final Supplier<Codec<DoubleCropDropsModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, DoubleCropDropsModifier::new)));

    public DoubleCropDropsModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for(LootItemCondition condition : this.conditions){
            if (!condition.test(context)){
                return generatedLoot;
            }
        }
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        Vec3 pos = context.getParamOrNull(LootContextParams.ORIGIN);

        if (pos != null && blockState != null && blockState.getBlock() instanceof CropBlock crop){
            BlockPos blockPos = BlockPos.containing(pos);
            Item seedItem = crop.getCloneItemStack(context.getLevel(), blockPos, blockState).getItem();

            List<ItemStack> addedLoot = new ArrayList<>();

            for (ItemStack itemStack : generatedLoot) {
                if (!itemStack.is(seedItem) && !itemStack.is(ModBlocks.TRELLIS.get().asItem())) {
                    addedLoot.add(itemStack.copy());
                }
            }
            generatedLoot.addAll(addedLoot);
        }



        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
