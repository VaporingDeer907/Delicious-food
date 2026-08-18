package net.mcreator.yummyfood.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class DoughItem extends Item {
   public DoughItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
   }
}
