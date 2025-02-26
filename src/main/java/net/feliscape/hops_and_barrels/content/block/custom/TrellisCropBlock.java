package net.feliscape.hops_and_barrels.content.block.custom;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.feliscape.hops_and_barrels.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public abstract class TrellisCropBlock extends CropBlock {
    public static final int MAX_HEIGHT = 2;
    public static IntegerProperty HEIGHT = IntegerProperty.create("height", 0, MAX_HEIGHT);
    protected static final float SEED_DROP_CHANCE = 0.1F;

    protected static final VoxelShape TRELLIS_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);

    public TrellisCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(BlockTags.DIRT) || pState.is(Blocks.FARMLAND) || pState.is(ModTags.Blocks.TRELLIS);
    }

    protected static VoxelShape[] createShapesByAge(int... radii) {
        VoxelShape[] shapes = new VoxelShape[radii.length];
        for (int i = 0; i < radii.length; i++){
            int radius = radii[i];
            shapes[i] = Block.box(8.0D - radius, 0.0D, 8.0D - radius,
                    8.0D + radius, 16.0D, 8.0D + radius);
        }
        return shapes;
    }

    protected abstract int getReturnHarvestAge();

    protected boolean canHarvest(BlockState pState){
        return this.isMaxAge(pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.getItemInHand(pHand).isEmpty() && this.canHarvest(pState)){
            if (!pLevel.isClientSide()) {
                if (pLevel.getRandom().nextFloat() <= SEED_DROP_CHANCE) {
                    popResourceFromFace(pLevel, pPos, pHit.getDirection(), new ItemStack(this.getBaseSeedId()));
                }
                getDrops(pState, (ServerLevel) pLevel, pPos, null, pPlayer, pPlayer.getItemInHand(pHand)).forEach((itemStack) -> {
                    if (!itemStack.is(ModBlocks.TRELLIS.get().asItem()))
                        popResourceFromFace(pLevel, pPos, pHit.getDirection(), itemStack);
                });
            }

            pLevel.setBlock(pPos, pState.setValue(this.getAgeProperty(), this.getReturnHarvestAge()), 3);
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }
        return InteractionResult.PASS;
    }

    public BlockState getStateForAge(int pAge, BlockState pState) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(pAge)).setValue(HEIGHT, pState.getValue(HEIGHT));
    }


    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return !this.isMaxAge(pState) || pState.getValue(HEIGHT) < MAX_HEIGHT;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int age = this.getAge(pState);
            int height = pState.getValue(HEIGHT);
            if (age >= this.getReturnHarvestAge() && height < MAX_HEIGHT && pRandom.nextInt(3) == 0){
                BlockPos abovePos = pPos.above();
                if (pLevel.getBlockState(abovePos).is(ModBlocks.TRELLIS.get())){
                    pLevel.setBlock(abovePos, this.getHeightState(pLevel, abovePos, pState), 3);
                }
            }
            if (age < this.getMaxAge()) {
                float f = getGrowthSpeed(this, pLevel, pPos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, this.getStateForAge(age + 1, pState), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }

    }

    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        int i = this.getAge(pState) + this.getBonemealAgeIncrease(pLevel);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        pLevel.setBlock(pPos, this.getStateForAge(i, pState), 2);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TRELLIS_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        int height = 0;
        for (int i = 0; i < MAX_HEIGHT; i++){
            BlockState belowState = level.getBlockState(clickedPos.below(i + 1));
            Brewery.printDebug(belowState.getBlock().getDescriptionId());
            if (belowState.is(ModTags.Blocks.TRELLIS)){
                height++;
            } else{
                break;
            }
        }

        return this.defaultBlockState().setValue(HEIGHT, height);
    }

    public BlockState getHeightState(Level pLevel, BlockPos pPos, BlockState pState){
        int height = 0;
        for (int i = 0; i < MAX_HEIGHT; i++){
            BlockState belowState = pLevel.getBlockState(pPos.below(i + 1));
            Brewery.printDebug(belowState.getBlock().getDescriptionId());
            if (belowState.is(ModTags.Blocks.TRELLIS)){
                height++;
            } else{
                break;
            }
        }

        return this.defaultBlockState().setValue(HEIGHT, height);
    }
}
