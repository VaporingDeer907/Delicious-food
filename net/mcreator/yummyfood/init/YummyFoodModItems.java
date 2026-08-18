package net.mcreator.yummyfood.init;

import net.mcreator.yummyfood.item.CleanwhiteradishItem;
import net.mcreator.yummyfood.item.DicedcarrotItem;
import net.mcreator.yummyfood.item.DoughItem;
import net.mcreator.yummyfood.item.DumplingsItem;
import net.mcreator.yummyfood.item.FlourItem;
import net.mcreator.yummyfood.item.GarlicItem;
import net.mcreator.yummyfood.item.KitchenkniveItem;
import net.mcreator.yummyfood.item.KuiGuaZiItem;
import net.mcreator.yummyfood.item.MashedgarlicItem;
import net.mcreator.yummyfood.item.MeatdumplingItem;
import net.mcreator.yummyfood.item.MungbeanItem;
import net.mcreator.yummyfood.item.MungbeansugarwaterItem;
import net.mcreator.yummyfood.item.PaddyItem;
import net.mcreator.yummyfood.item.Pea1Item;
import net.mcreator.yummyfood.item.PeacrispItem;
import net.mcreator.yummyfood.item.RadishsoupItem;
import net.mcreator.yummyfood.item.RawmincedporkItem;
import net.mcreator.yummyfood.item.SemifinishedstirfriedporkItem;
import net.mcreator.yummyfood.item.ShellofmelonseedItem;
import net.mcreator.yummyfood.item.StirfriedporkItem;
import net.mcreator.yummyfood.item.UndercookeddaikondumplingsItem;
import net.mcreator.yummyfood.item.UndercookedporkdumplingsItem;
import net.mcreator.yummyfood.item.WhiteradishItem;
import net.mcreator.yummyfood.item.WhiteradishcubeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyFoodModItems {
   public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "yummy_food");
   public static final RegistryObject<Item> DUMPLINGS = REGISTRY.register("dumplings", () -> new DumplingsItem());
   public static final RegistryObject<Item> RADISHSOUP = REGISTRY.register("radishsoup", () -> new RadishsoupItem());
   public static final RegistryObject<Item> WHITERADISH = REGISTRY.register("whiteradish", () -> new WhiteradishItem());
   public static final RegistryObject<Item> CLEANWHITERADISH = REGISTRY.register("cleanwhiteradish", () -> new CleanwhiteradishItem());
   public static final RegistryObject<Item> FLOUR = REGISTRY.register("flour", () -> new FlourItem());
   public static final RegistryObject<Item> DOUGH = REGISTRY.register("dough", () -> new DoughItem());
   public static final RegistryObject<Item> WHITERADISHCUBE = REGISTRY.register("whiteradishcube", () -> new WhiteradishcubeItem());
   public static final RegistryObject<Item> YE_SHENG_BAI_LUO_BU = block(YummyFoodModBlocks.YE_SHENG_BAI_LUO_BU);
   public static final RegistryObject<Item> PADDY = REGISTRY.register("paddy", () -> new PaddyItem());
   public static final RegistryObject<Item> RICE = doubleBlock(YummyFoodModBlocks.RICE);
   public static final RegistryObject<Item> PEA_1 = REGISTRY.register("pea_1", () -> new Pea1Item());
   public static final RegistryObject<Item> PEACRISP = REGISTRY.register("peacrisp", () -> new PeacrispItem());
   public static final RegistryObject<Item> MEATDUMPLING = REGISTRY.register("meatdumpling", () -> new MeatdumplingItem());
   public static final RegistryObject<Item> KITCHENKNIVE = REGISTRY.register("kitchenknive", () -> new KitchenkniveItem());
   public static final RegistryObject<Item> DICEDCARROT = REGISTRY.register("dicedcarrot", () -> new DicedcarrotItem());
   public static final RegistryObject<Item> MUNGBEAN = REGISTRY.register("mungbean", () -> new MungbeanItem());
   public static final RegistryObject<Item> MUNGBEASTRAIN = block(YummyFoodModBlocks.MUNGBEASTRAIN);
   public static final RegistryObject<Item> MUNGBEANSUGARWATER = REGISTRY.register("mungbeansugarwater", () -> new MungbeansugarwaterItem());
   public static final RegistryObject<Item> GARLIC = REGISTRY.register("garlic", () -> new GarlicItem());
   public static final RegistryObject<Item> MASHEDGARLIC = REGISTRY.register("mashedgarlic", () -> new MashedgarlicItem());
   public static final RegistryObject<Item> SEMIFINISHEDSTIRFRIEDPORK = REGISTRY.register(
      "semifinishedstirfriedpork", () -> new SemifinishedstirfriedporkItem()
   );
   public static final RegistryObject<Item> STIRFRIEDPORK = REGISTRY.register("stirfriedpork", () -> new StirfriedporkItem());
   public static final RegistryObject<Item> RAWMINCEDPORK = REGISTRY.register("rawmincedpork", () -> new RawmincedporkItem());
   public static final RegistryObject<Item> UNDERCOOKEDPORKDUMPLINGS = REGISTRY.register("undercookedporkdumplings", () -> new UndercookedporkdumplingsItem());
   public static final RegistryObject<Item> UNDERCOOKEDDAIKONDUMPLINGS = REGISTRY.register(
      "undercookeddaikondumplings", () -> new UndercookeddaikondumplingsItem()
   );
   public static final RegistryObject<Item> GA = block(YummyFoodModBlocks.GA);
   public static final RegistryObject<Item> KUI_GUA_ZI = REGISTRY.register("kui_gua_zi", () -> new KuiGuaZiItem());
   public static final RegistryObject<Item> SHELLOFMELONSEED = REGISTRY.register("shellofmelonseed", () -> new ShellofmelonseedItem());
   public static final RegistryObject<Item> ZHENGLU = block(YummyFoodModBlocks.ZHENGLU);

   private static RegistryObject<Item> block(RegistryObject<Block> block) {
      return REGISTRY.register(block.getId().getPath(), () -> new BlockItem((Block)block.get(), new Properties()));
   }

   private static RegistryObject<Item> doubleBlock(RegistryObject<Block> block) {
      return REGISTRY.register(block.getId().getPath(), () -> new DoubleHighBlockItem((Block)block.get(), new Properties()));
   }
}
