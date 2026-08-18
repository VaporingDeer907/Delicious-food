package net.mcreator.yummyfood.item;

import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class MashedgarlicItem extends Item {
   public MashedgarlicItem() {
      super(
         new Properties()
            .stacksTo(64)
            .rarity(Rarity.COMMON)
            .food(new Builder().nutrition(1).saturationMod(0.1F).build())
      );
   }

   public int getUseDuration(ItemStack itemstack) {
      return 10;
   }
}
