package net.feliscape.hops_and_barrels.block.entity;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.custom.JuicerBlock;
import net.feliscape.hops_and_barrels.recipe.JuicingRecipe;
import net.feliscape.hops_and_barrels.screen.JuicerMenu;
import net.feliscape.hops_and_barrels.sound.ModSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JuicerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT);

    public static final int RESULT_SLOT = 9;
    private static final int SLOT_COUNT = 10;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public JuicerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.JUICER_BE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.hops_and_barrels.juicer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new JuicerMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (itemHandler.getSlots() != SLOT_COUNT) itemHandler.setSize(SLOT_COUNT);
    }
    public boolean canInsertIngredients(){
        return this.getBlockState().getValue(JuicerBlock.LEVEL) == 0;
    }

    public boolean hasRecipe(){
        Optional<JuicingRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()){
            return false;
        }
        ItemStack result = recipe.get().getResultItem(null);

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(RESULT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(RESULT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(RESULT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(RESULT_SLOT).getMaxStackSize();
    }

    private Optional<JuicingRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots() - 1);
        for (int i = 0; i < itemHandler.getSlots() - 1; i++){
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(JuicingRecipe.Type.INSTANCE, inventory, level);
    }

    private ItemStack getCraftingResult() {
        Optional<JuicingRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()){
            return ItemStack.EMPTY;
        }

        return recipe.get().getResultItem(null);
    }

    private void craftItem() {
        Optional<JuicingRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        for (int i = 0; i < SLOT_COUNT - 1; i++){
            this.itemHandler.extractItem(i, 1, false);
        }

        this.itemHandler.setStackInSlot(RESULT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(RESULT_SLOT).getCount() + result.getCount()));
    }

    public void pressDown(Level pLevel, BlockState pState, BlockPos pPos) {
        if (pLevel.isClientSide()) return;

        int stateLevel = pState.getValue(JuicerBlock.LEVEL);
        if (hasRecipe()){
            stateLevel++;
            pState = pState.setValue(JuicerBlock.LEVEL, stateLevel);
            pLevel.setBlock(pPos, pState, 3);
            pLevel.playSound(null, pPos, ModSounds.JUICER_CRUSH.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            spawnCrushParticles(pLevel, pState, pPos);
            if (stateLevel >= 6){
                craftItem();
            }
            setChanged(pLevel, pPos, pState);
        }
    }

    private void spawnCrushParticles(Level pLevel, BlockState pState, BlockPos pPos) {
        Vec3 pos = pPos.getCenter();
        RandomSource random = pLevel.getRandom();
        int juicerLevel = pState.getValue(JuicerBlock.LEVEL);
        for (int i = 0; i < itemHandler.getSlots() - 1; i++){
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;

            Vec3 offset = new Vec3(((double)random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D,
                    ((double)random.nextFloat() - 0.5D) * 0.1D);
            double xPos = pos.x + ((double)random.nextFloat() - 0.5D) * 0.75D;
            double yPos = pPos.getY() + (1 + (7 - juicerLevel) * 2) / 16.0D;
            double zPos = pos.z + ((double)random.nextFloat() - 0.5D) * 0.75D;

            ((ServerLevel) pLevel).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, itemStack),
                    xPos, yPos, zPos, 3, offset.x, offset.y, offset.z, 0.2D);
        }
    }

    public boolean isCrafting() {
        return this.getBlockState().getValue(JuicerBlock.LEVEL) != 0;
    }

    public boolean hasIngredients() {
        for (int i = 0; i < itemHandler.getSlots() - 1; i++){
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (!itemStack.isEmpty()) return true;
        }
        return false;
    }
}
