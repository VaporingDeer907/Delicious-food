package net.mcreator.yummyfood.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import net.mcreator.yummyfood.world.inventory.ZhengMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ZhengScreen extends AbstractContainerScreen<ZhengMenu> {
   private static final HashMap<String, Object> guistate = ZhengMenu.guistate;
   private final Level world;
   private final int x;
   private final int y;
   private final int z;
   private final Player entity;
   private static final ResourceLocation texture = ResourceLocation.tryParse("yummy_food:textures/screens/zheng.png");
   private static final ResourceLocation slotTexture = ResourceLocation.tryParse("minecraft:textures/gui/container/generic_54.png");
   private static final ResourceLocation furnaceTexture = ResourceLocation.tryParse("minecraft:textures/gui/container/furnace.png");

   public ZhengScreen(ZhengMenu container, Inventory inventory, Component text) {
      super(container, inventory, text);
      this.world = container.world;
      this.x = container.x;
      this.y = container.y;
      this.z = container.z;
      this.entity = container.entity;
      this.imageWidth = 176;
      this.imageHeight = 166;
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(guiGraphics);
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderTexture(0, texture);
      guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

      // 水槽与燃料槽没有现成的背景画格，从原版箱子贴图截取槽位框
      guiGraphics.blit(slotTexture, this.leftPos + 26, this.topPos + 17, 7, 17, 18, 18);
      guiGraphics.blit(slotTexture, this.leftPos + 26, this.topPos + 53, 7, 17, 18, 18);

      // 火焰指示（数据槽：0=剩余燃烧tick，1=单块煤总时长）
      int burn = this.menu.data.get(0);
      int maxBurn = this.menu.data.get(1);
      if (maxBurn > 0) {
         int l = 13 - burn * 13 / maxBurn;
         if (l > 0) {
            guiGraphics.blit(furnaceTexture, this.leftPos + 45, this.topPos + 38 + 12 - l, 176, 12 - l, 14, l + 1);
         }
      }

      // 进度箭头（数据槽：2=当前进度tick，3=总时长20秒）
      int cook = this.menu.data.get(2);
      int cookTime = this.menu.data.get(3);
      if (cookTime > 0) {
         int k = cook * 24 / cookTime;
         if (k > 0) {
            guiGraphics.blit(furnaceTexture, this.leftPos + 80, this.topPos + 36, 176, 14, k + 1, 16);
         }
      }

      RenderSystem.disableBlend();
   }

   public boolean keyPressed(int key, int b, int c) {
      if (key == 256) {
         this.minecraft.player.closeContainer();
         return true;
      } else {
         return super.keyPressed(key, b, c);
      }
   }

   public void containerTick() {
      super.containerTick();
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      guiGraphics.drawString(this.font, Component.translatable("gui.yummy_food.zheng.label_zheng_lu"), 82, 5, -12829636);
   }
}
