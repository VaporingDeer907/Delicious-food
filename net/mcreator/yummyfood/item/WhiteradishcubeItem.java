package net.mcreator.yummyfood.item;

import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class WhiteradishcubeItem extends Item {
   public WhiteradishcubeItem() {
      super(
         new Properties()
            .stacksTo(64)
            .rarity(Rarity.COMMON)
            .food(new Builder().nutrition(4).saturationMod(0.3F).build())
      );
   }

   public int getUseDuration(ItemStack itemstack) {
      return 24;
   }
}
