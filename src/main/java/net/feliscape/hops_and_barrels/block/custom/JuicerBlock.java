package net.feliscape.hops_and_barrels.block.custom;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.entity.JuicerBlockEntity;
import net.feliscape.hops_and_barrels.block.entity.ModBlockEntities;
import net.feliscape.hops_and_barrels.util.MathUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class JuicerBlock extends BaseEntityBlock{
    private static final VoxelShape OUTER_SHAPE = Shapes.block();
    private static final VoxelShape[] SHAPES = Util.make(new VoxelShape[7], (voxelShapes) -> {
        for(int i = 0; i < 7; ++i) {
            voxelShapes[i] = Shapes.join(OUTER_SHAPE, Block.box(2.0D, 1 + (7 - i) * 2, 2.0D,
                    14.0D, 16.0D, 14.0D), BooleanOp.ONLY_FIRST);
        }
    });

    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 6);

    public JuicerBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES[pState.getValue(LEVEL)];
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof JuicerBlockEntity) {
                ItemStack itemStack = pPlayer.getItemInHand(pHand);
                if (itemStack.isEmpty() && pPlayer.isShiftKeyDown()){ // Reset piston
                    pLevel.setBlock(pPos, pState.setValue(LEVEL, 0), 3);
                } else{
                    NetworkHooks.openScreen(((ServerPlayer)pPlayer), (JuicerBlockEntity)entity, pPos);
                }
            } else {
                throw new IllegalStateException("Missing Container Provider");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new JuicerBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof JuicerBlockEntity) {
                ((JuicerBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LEVEL);
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pFallDistance > 0.5F){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof JuicerBlockEntity) {
                if (pState.getValue(LEVEL) < 6 && pEntity.position().y < pPos.getY() + 1) { // can press down
                    ((JuicerBlockEntity) blockEntity).pressDown(pLevel, pState, pPos);
                }
            }
        }
        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
    }
}
