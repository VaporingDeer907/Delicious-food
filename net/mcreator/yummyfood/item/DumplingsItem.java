package net.mcreator.yummyfood.item;

import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class DumplingsItem extends Item {
   public DumplingsItem() {
      super(
         new Properties()
            .stacksTo(64)
            .rarity(Rarity.COMMON)
            .food(new Builder().nutrition(6).saturationMod(1.0F).build())
      );
   }
}
