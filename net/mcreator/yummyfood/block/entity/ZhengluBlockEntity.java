package net.mcreator.yummyfood.block.entity;

import io.netty.buffer.Unpooled;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.mcreator.yummyfood.block.ZhengluBlock;
import net.mcreator.yummyfood.init.YummyFoodModBlockEntities;
import net.mcreator.yummyfood.init.YummyFoodModItems;
import net.mcreator.yummyfood.world.inventory.ZhengMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class ZhengluBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
   // Slot layout: 0 = 水(水桶), 1 = 燃料(煤炭/木炭), 2 = 食材(仅模组物品), 3 = 成品
   public static final int SLOT_WATER = 0;
   public static final int SLOT_FUEL = 1;
   public static final int SLOT_INPUT = 2;
   public static final int SLOT_OUTPUT = 3;

   // 一块煤炭/木炭 + 一桶水，持续燃烧 120 秒（20 tick/秒）
   public static final int FUEL_BURN_TIME = 120 * 20;
   // 一件物品需要 20 秒蒸熟
   public static final int COOK_TIME = 20 * 20;

   private NonNullList<ItemStack> stacks = NonNullList.withSize(4, ItemStack.EMPTY);
   private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

   public int burnTime;
   public int maxBurnTime;
   public int cookProgress;

   // 供 GUI 同步的数据槽：[0]=burnTime, [1]=maxBurnTime, [2]=cookProgress, [3]=cookTime
   public final ContainerData dataAccess = new SimpleContainerData(4);

   public ZhengluBlockEntity(BlockPos position, BlockState state) {
      super((BlockEntityType)YummyFoodModBlockEntities.ZHENGLU.get(), position, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, ZhengluBlockEntity be) {
      if (level.isClientSide) {
         // 客户端：燃烧时冒蒸汽
         if (state.getValue(ZhengluBlock.LIT)) {
            RandomSource r = level.random;
            if (r.nextInt(8) == 0) {
               level.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.2 + r.nextDouble() * 0.6, pos.getY() + 1.05,
                     pos.getZ() + 0.2 + r.nextDouble() * 0.6, 0.0, 0.04 + r.nextDouble() * 0.03, 0.0);
            }
         }
         return;
      }

      boolean hasWater = be.hasWater();
      boolean burning = false;

      // 燃烧计时：点燃后持续 120 秒
      if (be.burnTime > 0) {
         be.burnTime--;
         burning = true;
      }
      // 点燃：需要 1 块煤（或木炭）+ 1 桶水，两者同时消耗（水桶留下空桶）
      if (be.burnTime <= 0 && isFuel(be.getItem(SLOT_FUEL)) && hasWater) {
         be.getItem(SLOT_FUEL).shrink(1);
         ItemStack water = be.getItem(SLOT_WATER);
         water.shrink(1);
         if (water.isEmpty()) {
            be.setItem(SLOT_WATER, new ItemStack(Items.BUCKET));
         }
         be.burnTime = FUEL_BURN_TIME;
         be.maxBurnTime = FUEL_BURN_TIME;
         burning = true;
      }

      // 烹饪：20 秒一件
      Item result = steamResult(be.getItem(SLOT_INPUT).getItem());
      if (burning && result != null && canOutput(be, result)) {
         be.cookProgress++;
         if (be.cookProgress >= COOK_TIME) {
            be.cookProgress = 0;
            be.getItem(SLOT_INPUT).shrink(1);
            ItemStack out = be.getItem(SLOT_OUTPUT);
            if (out.isEmpty()) {
               be.setItem(SLOT_OUTPUT, new ItemStack(result));
            } else {
               out.grow(1);
            }
            be.setChanged();
         }
      } else if (!burning || result == null) {
         be.cookProgress = 0;
      }

      // 同步 LIT 方块状态（驱动粒子与客户端更新）
      BlockState bs = level.getBlockState(pos);
      if (bs.getValue(ZhengluBlock.LIT) != burning) {
         level.setBlock(pos, bs.setValue(ZhengluBlock.LIT, burning), 3);
      }

      // 更新 GUI 数据槽（服务端 push 到客户端）
      be.dataAccess.set(0, be.burnTime);
      be.dataAccess.set(1, be.maxBurnTime);
      be.dataAccess.set(2, be.cookProgress);
      be.dataAccess.set(3, COOK_TIME);

      be.setChanged();
   }

   /** 蒸制配方：食材 -> 成品（全部为模组内物品）。无配方返回 null。 */
   public static Item steamResult(Item item) {
      if (item == YummyFoodModItems.UNDERCOOKEDPORKDUMPLINGS.get()) {
         return YummyFoodModItems.MEATDUMPLING.get();
      }
      if (item == YummyFoodModItems.UNDERCOOKEDDAIKONDUMPLINGS.get()) {
         return YummyFoodModItems.DUMPLINGS.get();
      }
      if (item == YummyFoodModItems.SEMIFINISHEDSTIRFRIEDPORK.get()) {
         return YummyFoodModItems.STIRFRIEDPORK.get();
      }
      if (item == YummyFoodModItems.PEA_1.get()) {
         return YummyFoodModItems.PEACRISP.get();
      }
      return null;
   }

   public static boolean isFuel(ItemStack stack) {
      Item item = stack.getItem();
      return item == Items.COAL || item == Items.CHARCOAL;
   }

   public boolean hasWater() {
      return this.getItem(SLOT_WATER).is(Items.WATER_BUCKET);
   }

   private static boolean canOutput(ZhengluBlockEntity be, Item result) {
      ItemStack out = be.getItem(SLOT_OUTPUT);
      return out.isEmpty() || (out.is(result) && out.getCount() < out.getMaxStackSize());
   }

   public void load(CompoundTag compound) {
      super.load(compound);
      if (!this.tryLoadLootTable(compound)) {
         this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      }
      ContainerHelper.loadAllItems(compound, this.stacks);
      this.burnTime = compound.getInt("BurnTime");
      this.maxBurnTime = compound.getInt("MaxBurnTime");
      this.cookProgress = compound.getInt("CookProgress");
   }

   public void saveAdditional(CompoundTag compound) {
      super.saveAdditional(compound);
      if (!this.trySaveLootTable(compound)) {
         ContainerHelper.saveAllItems(compound, this.stacks);
      }
      compound.putInt("BurnTime", this.burnTime);
      compound.putInt("MaxBurnTime", this.maxBurnTime);
      compound.putInt("CookProgress", this.cookProgress);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag() {
      return this.saveWithFullMetadata();
   }

   public int getContainerSize() {
      return this.stacks.size();
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.stacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }
      return true;
   }

   public Component getDefaultName() {
      return Component.literal("zhenglu");
   }

   public int getMaxStackSize() {
      return 64;
   }

   public AbstractContainerMenu createMenu(int id, Inventory inventory) {
      return new ZhengMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition));
   }

   public Component getDisplayName() {
      return Component.literal("蒸炉");
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }

   protected void setItems(NonNullList<ItemStack> stacks) {
      this.stacks = stacks;
   }

   public boolean canPlaceItem(int index, ItemStack stack) {
      return switch (index) {
         case SLOT_WATER -> stack.is(Items.WATER_BUCKET);
         case SLOT_FUEL -> isFuel(stack);
         case SLOT_INPUT -> steamResult(stack.getItem()) != null;
         default -> false;
      };
   }

   public int[] getSlotsForFace(Direction side) {
      return IntStream.range(0, this.getContainerSize()).toArray();
   }

   public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
      return this.canPlaceItem(index, stack);
   }

   public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
      return index == SLOT_OUTPUT;
   }

   public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
      return !this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER
         ? this.handlers[facing.ordinal()].cast()
         : super.getCapability(capability, facing);
   }

   public void setRemoved() {
      super.setRemoved();
      for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
         handler.invalidate();
      }
   }
}
