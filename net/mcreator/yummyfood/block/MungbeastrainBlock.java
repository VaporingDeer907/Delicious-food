package net.mcreator.yummyfood.block;

import java.util.Collections;
import java.util.List;
import net.mcreator.yummyfood.init.YummyFoodModBlocks;
import net.mcreator.yummyfood.init.YummyFoodModItems;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent.Block;

public class MungbeastrainBlock extends FlowerBlock {
   public MungbeastrainBlock() {
      super(MobEffects.POISON, 100, Properties.of().mapColor(MapColor.PLANT).sound(SoundType.GRASS).instabreak().noCollission());
   }

   public int getEffectDuration() {
      return 100;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 100;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }

   public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
      List<ItemStack> dropsOriginal = super.getDrops(state, builder);
      return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack((ItemLike)YummyFoodModItems.MUNGBEAN.get()));
   }

   @OnlyIn(Dist.CLIENT)
   public static void blockColorLoad(Block event) {
      event.getBlockColors()
         .register(
            (bs, world, pos, index) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5, 1.0),
            new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)YummyFoodModBlocks.MUNGBEASTRAIN.get()}
         );
   }
}
