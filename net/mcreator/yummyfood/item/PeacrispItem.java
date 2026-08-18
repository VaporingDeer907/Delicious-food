package net.mcreator.yummyfood.item;

import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class PeacrispItem extends Item {
   public PeacrispItem() {
      super(
         new Properties()
            .stacksTo(64)
            .rarity(Rarity.COMMON)
            .food(new Builder().nutrition(1).saturationMod(0.3F).build())
      );
   }

   public int getUseDuration(ItemStack itemstack) {
      return 18;
   }
}
