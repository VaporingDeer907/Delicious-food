package net.mcreator.yummyfood.block;

import java.util.Collections;
import java.util.List;
import net.mcreator.yummyfood.init.YummyFoodModBlocks;
import net.mcreator.yummyfood.init.YummyFoodModItems;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent.Block;
import net.minecraftforge.common.PlantType;

public class RiceBlock extends DoublePlantBlock implements BonemealableBlock {
   public RiceBlock() {
      super(Properties.of().mapColor(MapColor.PLANT).sound(SoundType.GRASS).instabreak().noCollission());
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 100;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }

   public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
      if (state.getValue(HALF) != DoubleBlockHalf.LOWER) {
         return Collections.emptyList();
      } else {
         List<ItemStack> dropsOriginal = super.getDrops(state, builder);
         return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack((ItemLike)YummyFoodModItems.PADDY.get(), 2));
      }
   }

   public boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
      return groundState.is(Blocks.DIRT) || groundState.is(Blocks.GRASS_BLOCK);
   }

   public boolean canSurvive(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.below();
      BlockState groundState = worldIn.getBlockState(blockpos);
      return blockstate.getValue(HALF) != DoubleBlockHalf.UPPER
         ? this.mayPlaceOn(groundState, worldIn, blockpos)
         : groundState.is(this) && groundState.getValue(HALF) == DoubleBlockHalf.LOWER;
   }

   public PlantType getPlantType(BlockGetter world, BlockPos pos) {
      return PlantType.PLAINS;
   }

   public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState blockstate, boolean clientSide) {
      return true;
   }

   public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState blockstate) {
      return true;
   }

   public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState blockstate) {
   }

   @OnlyIn(Dist.CLIENT)
   public static void blockColorLoad(Block event) {
      event.getBlockColors()
         .register(
            (bs, world, pos, index) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5, 1.0),
            new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)YummyFoodModBlocks.RICE.get()}
         );
   }
}
