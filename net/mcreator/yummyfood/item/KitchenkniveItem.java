package net.mcreator.yummyfood.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;

public class KitchenkniveItem extends SwordItem {
   public KitchenkniveItem() {
      super(new Tier() {
         public int getUses() {
            return 522;
         }

         public float getSpeed() {
            return 4.0F;
         }

         public float getAttackDamageBonus() {
            return 4.0F;
         }

         public int getLevel() {
            return 1;
         }

         public int getEnchantmentValue() {
            return 2;
         }

         public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack[]{new ItemStack(Items.IRON_INGOT)});
         }
      }, 3, -3.0F, new Properties());
   }

   public boolean hasCraftingRemainingItem(ItemStack stack) {
      return true;
   }

   public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
      return new ItemStack(this);
   }

   public boolean isRepairable(ItemStack itemstack) {
      return false;
   }
}
