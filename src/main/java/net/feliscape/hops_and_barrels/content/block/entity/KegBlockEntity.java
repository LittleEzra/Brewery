package net.feliscape.hops_and_barrels.content.block.entity;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.content.block.custom.KegBlock;
import net.feliscape.hops_and_barrels.content.recipe.KegBrewingRecipe;
import net.feliscape.hops_and_barrels.client.screen.KegMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class KegBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT);

    private static final int INGREDIENT_SLOT_0 = 0;
    private static final int INGREDIENT_SLOT_1 = 1;
    private static final int CONTAINER_SLOT = 2;
    private static final int RESULT_SLOT = 3;

    private static final int SLOT_COUNT = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 360;
    private int waterAmount = 0;
    private int maxWaterAmount = 15;

    public KegBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.KEG_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> KegBlockEntity.this.progress;
                    case 1 -> KegBlockEntity.this.maxProgress;
                    case 2 -> KegBlockEntity.this.waterAmount;
                    case 3 -> KegBlockEntity.this.maxWaterAmount;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> KegBlockEntity.this.progress = pValue;
                    case 1 -> KegBlockEntity.this.maxProgress = pValue;
                    case 2 -> KegBlockEntity.this.waterAmount = pValue;
                    case 3 -> KegBlockEntity.this.maxWaterAmount = pValue;
                };
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
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
        return Component.translatable("container.hops_and_barrels.keg");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new KegMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("keg.progress", progress);
        pTag.putInt("keg.water", waterAmount);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (itemHandler.getSlots() != SLOT_COUNT) itemHandler.setSize(SLOT_COUNT);
        progress = pTag.getInt("keg.progress");
        waterAmount = pTag.getInt("keg.water");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean brewing = this.isBrewing();
        boolean changed = false;
        if(hasRecipe()){
            progress++;
            changed = true;

            if (progress >= maxProgress) {
                craftItem();
                resetProgress();
            }
        } else{
            resetProgress();
        }

        if (brewing != this.isBrewing()){
            changed = true;
            pState = pState.setValue(KegBlock.BREWING, brewing);
            pLevel.setBlock(pPos, pState, 3);
        }

        if (changed){
            setChanged(pLevel, pPos, pState);
        }
    }

    private boolean isBrewing() {
        return progress > 0;
    }

    private void resetProgress() {
        progress = 0;
    }

    public boolean hasRecipe(){
        if (waterAmount < 1) return false;

        Optional<KegBrewingRecipe> recipe = getCurrentRecipe();

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

    private Optional<KegBrewingRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(KegBrewingRecipe.Type.INSTANCE, inventory, level);
    }

    private ItemStack getCraftingResult() {
        Optional<KegBrewingRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()){
            return ItemStack.EMPTY;
        }

        return recipe.get().getResultItem(null);
    }
    public ItemStack addWaterFromItemStack(ItemStack pStack, Player pPlayer){
        if (pStack.is(Items.WATER_BUCKET)){
            if (waterAmount > maxWaterAmount - 3){
                return pStack;
            }
            waterAmount = Math.min(maxWaterAmount, waterAmount + 3); // Each bucket is worth 3 units
            return new ItemStack(Items.BUCKET);
        }
        if (pStack.is(Items.POTION) && PotionUtils.getPotion(pStack) == Potions.WATER){
            if (waterAmount > maxWaterAmount - 1){
                return pStack;
            }
            waterAmount = Math.min(maxWaterAmount, waterAmount + 1); // Each bottle is worth 1 unit

            if (pPlayer != null) {
                pPlayer.awardStat(Stats.ITEM_USED.get(pStack.getItem()));
                if (!pPlayer.getAbilities().instabuild) {
                    pStack.shrink(1);
                }
            }

            if (pPlayer == null || !pPlayer.getAbilities().instabuild) {
                if (pStack.isEmpty()) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }

                if (pPlayer != null) {
                    pPlayer.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
                }
            }
            return pStack;
        }
        Brewery.printDebug("WARNING: ItemStack is not a Water Bucket or Water Bottle");
        return pStack;
    }

    private void craftItem() {
        Optional<KegBrewingRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        waterAmount--;

        this.itemHandler.extractItem(CONTAINER_SLOT, 1, false);
        this.itemHandler.extractItem(INGREDIENT_SLOT_0, 1, false);
        this.itemHandler.extractItem(INGREDIENT_SLOT_1, 1, false);

        this.itemHandler.setStackInSlot(RESULT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(RESULT_SLOT).getCount() + result.getCount()));
    }
}
