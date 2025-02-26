package net.feliscape.hops_and_barrels.content.item.custom;

import net.feliscape.hops_and_barrels.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BarleyItem extends Item {
    public BarleyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = pContext.getPlayer();
        ItemStack itemStack = pContext.getItemInHand();
        if (player != null && state.is(Blocks.WATER_CAULDRON)){
            LayeredCauldronBlock.lowerFillLevel(state, level, pos);

            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            if (itemStack.isEmpty()) {
                player.setItemInHand(pContext.getHand(), new ItemStack(ModItems.MALTED_BARLEY.get()));
            } else{
                player.getInventory().add(new ItemStack(ModItems.MALTED_BARLEY.get()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
    }
}
