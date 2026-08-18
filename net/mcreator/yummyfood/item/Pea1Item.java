package net.mcreator.yummyfood.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class Pea1Item extends Item {
   public Pea1Item() {
      super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
   }
}
