package net.minecraft.client.renderer.texture.atlas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader.1;
import net.minecraft.client.renderer.texture.atlas.SpriteSource.Output;
import net.minecraft.client.renderer.texture.atlas.SpriteSource.SpriteSupplier;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class SpriteResourceLoader {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final FileToIdConverter ATLAS_INFO_CONVERTER = new FileToIdConverter("atlases", ".json");
   private final List<SpriteSource> sources;

   private SpriteResourceLoader(List<SpriteSource> p_261613_) {
      this.sources = p_261613_;
   }

   public List<Supplier<SpriteContents>> list(ResourceManager p_261989_) {
      Map<ResourceLocation, SpriteSupplier> $$1 = new HashMap<>();
      Output $$2 = new 1(this, $$1);
      this.sources.forEach(p_261747_ -> p_261747_.run(p_261989_, $$2));
      Builder<Supplier<SpriteContents>> $$3 = ImmutableList.builder();
      $$3.add(MissingTextureAtlasSprite::create);
      $$3.addAll($$1.values());
      return $$3.build();
   }

   public static SpriteResourceLoader load(ResourceManager p_261551_, ResourceLocation p_261709_) {
      ResourceLocation $$2 = ATLAS_INFO_CONVERTER.idToFile(p_261709_);
      List<SpriteSource> $$3 = new ArrayList<>();

      for (Resource $$4 : p_261551_.getResourceStack($$2)) {
         try (BufferedReader $$5 = $$4.openAsReader()) {
            Dynamic<JsonElement> $$6 = new Dynamic(JsonOps.INSTANCE, JsonParser.parseReader($$5));
            $$3.addAll((Collection<? extends SpriteSource>)SpriteSources.FILE_CODEC.parse($$6).getOrThrow(false, LOGGER::error));
         } catch (Exception var11) {
            LOGGER.warn("Failed to parse atlas definition {} in pack {}", new Object[]{$$2, $$4.sourcePackId(), var11});
         }
      }

      return new SpriteResourceLoader($$3);
   }
}
