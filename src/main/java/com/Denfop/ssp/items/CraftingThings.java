package com.Denfop.ssp.items;

import ic2.core.block.state.IIdProvider;
import ic2.core.init.BlocksItems;
import ic2.core.item.ItemMulti;
import ic2.core.ref.ItemName;
import java.util.Locale;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CraftingThings extends ItemMulti<com.chocohead.advsolar.items.ItemCraftingThings.CraftingTypes> {
   protected static final String NAME = "crafting";

   public CraftingThings() {
      super((ItemName)null, (Class) CraftingThings.CraftingTypes.class);
      ((CraftingThings)BlocksItems.registerItem(this, new ResourceLocation("super_solar_panels", "crafting"))).setTranslationKey("crafting");
   }

   @SideOnly(Side.CLIENT)
   protected void registerModel(int meta, ItemName name, String extraName) {
      ModelLoader.setCustomModelResourceLocation(
         this, meta, new ModelResourceLocation("super_solar_panels:crafting/" + CraftingThings.CraftingTypes.getFromID(meta).getName(), (String)null)
      );
   }

   public String getTranslationKey() {
      return "super_solar_panels." + super.getTranslationKey().substring(4);
   }

   public static enum CraftingTypes implements IIdProvider {
      enderquantumcomponent(0),
      solarsplitter(1),
      bluecomponent(2),
      redcomponent(4),
      singularcore(5),
      spectralcore(6),
      EnrichedSunnariumAlloy2(7),
      EnrichedSunnariumAlloy3(8),
      quantcore1(9),
      quantcore2(10),
      nanobox(11),
      QuantumItems2(12),
      QuantumItems3(13),
      QuantumItems4(14),
      QuantumItems5(15),
      photoniy_ingot(16),
      photoniy(17),
      quantumitems6(18),
      advanced_core(19),
      hybrid_core(20),
      ultimate_core(21),
      compresscarbon(22),
      coal_chunk(23),
      compresscarbonultra(24),
      rune_sun(25),
      rune_night(26),
      rune_energy(27);

      private final String name = this.name().toLowerCase(Locale.ENGLISH);
      private final int ID;
      private static final CraftingThings.CraftingTypes[] VALUES = values();

      private CraftingTypes(int ID) {
         this.ID = ID;
      }

      public String getName() {
         return this.name;
      }

      public int getId() {
         return this.ID;
      }

      public static CraftingThings.CraftingTypes getFromID(int ID) {
         // 按显式 ID 字段查找，枚举声明顺序与 ID 不再强制一一对应
         for (CraftingThings.CraftingTypes type : VALUES) {
            if (type.ID == ID) {
               return type;
            }
         }

         return VALUES[0];
      }
   }
}
