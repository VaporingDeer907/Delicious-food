package net.mcreator.yummyfood.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YummyFoodModTabs {
   public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "yummy_food");
   public static final RegistryObject<CreativeModeTab> TAB_DELICIOUSFOOD = REGISTRY.register("tabdeliciousfood", () -> CreativeModeTab.builder()
      .title(Component.translatable("itemGroup.tabdeliciousfood"))
      .icon(() -> new ItemStack(YummyFoodModItems.RADISHSOUP.get()))
      .displayItems((params, output) -> {
         output.accept(YummyFoodModItems.DUMPLINGS.get());
         output.accept(YummyFoodModItems.RADISHSOUP.get());
         output.accept(YummyFoodModItems.WHITERADISH.get());
         output.accept(YummyFoodModItems.CLEANWHITERADISH.get());
         output.accept(YummyFoodModItems.FLOUR.get());
         output.accept(YummyFoodModItems.DOUGH.get());
         output.accept(YummyFoodModItems.WHITERADISHCUBE.get());
         output.accept(YummyFoodModItems.YE_SHENG_BAI_LUO_BU.get());
         output.accept(YummyFoodModItems.PADDY.get());
         output.accept(YummyFoodModItems.RICE.get());
         output.accept(YummyFoodModItems.PEA_1.get());
         output.accept(YummyFoodModItems.PEACRISP.get());
         output.accept(YummyFoodModItems.MEATDUMPLING.get());
         output.accept(YummyFoodModItems.KITCHENKNIVE.get());
         output.accept(YummyFoodModItems.DICEDCARROT.get());
         output.accept(YummyFoodModItems.MUNGBEAN.get());
         output.accept(YummyFoodModItems.MUNGBEASTRAIN.get());
         output.accept(YummyFoodModItems.MUNGBEANSUGARWATER.get());
         output.accept(YummyFoodModItems.GARLIC.get());
         output.accept(YummyFoodModItems.MASHEDGARLIC.get());
         output.accept(YummyFoodModItems.SEMIFINISHEDSTIRFRIEDPORK.get());
         output.accept(YummyFoodModItems.STIRFRIEDPORK.get());
         output.accept(YummyFoodModItems.RAWMINCEDPORK.get());
         output.accept(YummyFoodModItems.UNDERCOOKEDPORKDUMPLINGS.get());
         output.accept(YummyFoodModItems.UNDERCOOKEDDAIKONDUMPLINGS.get());
         output.accept(YummyFoodModItems.GA.get());
         output.accept(YummyFoodModItems.KUI_GUA_ZI.get());
         output.accept(YummyFoodModItems.SHELLOFMELONSEED.get());
         output.accept(YummyFoodModItems.ZHENGLU.get());
      })
      .build());
}
