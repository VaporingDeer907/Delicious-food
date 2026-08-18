package net.mcreator.yummyfood.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class MungbeanItem extends Item {
   public MungbeanItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
   }
}
