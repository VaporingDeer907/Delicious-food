package net.mcreator.yummyfood.init;

import net.mcreator.yummyfood.world.inventory.ZhengMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyFoodModMenus {
   public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "yummy_food");
   public static final RegistryObject<MenuType<ZhengMenu>> ZHENG = REGISTRY.register("zheng", () -> IForgeMenuType.create(ZhengMenu::new));
}
