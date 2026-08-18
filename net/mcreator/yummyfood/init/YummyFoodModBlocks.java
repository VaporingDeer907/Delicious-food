package net.mcreator.yummyfood.init;

import net.mcreator.yummyfood.block.GaBlock;
import net.mcreator.yummyfood.block.MungbeastrainBlock;
import net.mcreator.yummyfood.block.RiceBlock;
import net.mcreator.yummyfood.block.YeShengBaiLuoBuBlock;
import net.mcreator.yummyfood.block.ZhengluBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyFoodModBlocks {
   public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "yummy_food");
   public static final RegistryObject<Block> YE_SHENG_BAI_LUO_BU = REGISTRY.register("ye_sheng_bai_luo_bu", () -> new YeShengBaiLuoBuBlock());
   public static final RegistryObject<Block> RICE = REGISTRY.register("rice", () -> new RiceBlock());
   public static final RegistryObject<Block> MUNGBEASTRAIN = REGISTRY.register("mungbeastrain", () -> new MungbeastrainBlock());
   public static final RegistryObject<Block> GA = REGISTRY.register("ga", () -> new GaBlock());
   public static final RegistryObject<Block> ZHENGLU = REGISTRY.register("zhenglu", () -> new ZhengluBlock());

   @EventBusSubscriber(
      bus = Bus.MOD,
      value = {Dist.CLIENT}
   )
   public static class ClientSideHandler {
      @SubscribeEvent
      public static void blockColorLoad(net.minecraftforge.client.event.RegisterColorHandlersEvent.Block event) {
         YeShengBaiLuoBuBlock.blockColorLoad(event);
         RiceBlock.blockColorLoad(event);
         MungbeastrainBlock.blockColorLoad(event);
      }
   }
}
