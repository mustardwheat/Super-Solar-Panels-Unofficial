package com.denfop.ssp.items;

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

public class CraftingThings extends ItemMulti<CraftingThings.CraftingTypes> {
   protected static final String NAME = "crafting";

   public CraftingThings() {
      super((ItemName)null, CraftingThings.CraftingTypes.class);
      ((CraftingThings)BlocksItems.registerItem(this, new ResourceLocation("super_solar_panels", NAME))).setTranslationKey(NAME);
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

   // 变体顺序（创造标签页展示顺序）按材料到核心的递进重排，ID 连续编号；
   // 本次重排为破坏性变更，不兼容旧存档中已持有的本物品变体
   public static enum CraftingTypes implements IIdProvider {
      enderquantumcomponent(0),
      solarsplitter(1),
      bluecomponent(2),
      redcomponent(3),
      QuantumItems2(4),
      QuantumItems4(5),
      nanobox(6),
      QuantumItems3(7),
      quantumitems6(8),
      QuantumItems5(9),
      photoniy(10),
      photoniy_ingot(11),
      EnrichedSunnariumAlloy3(12),
      EnrichedSunnariumAlloy2(13),
      advanced_core(14),
      hybrid_core(15),
      ultimate_core(16),
      spectralcore(17),
      singularcore(18),
      quantcore2(19),
      quantcore1(20),
      neutroncore(21),
      neutron(22),
      coal_chunk(23),
      compresscarbon(24),
      compresscarbonultra(25),
      rune_sun(26),
      rune_night(27),
      rune_energy(28);

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
         // 按显式 ID 字段查找（声明顺序与 ID 一致，越界时回退首个变体）
         for (CraftingThings.CraftingTypes type : VALUES) {
            if (type.ID == ID) {
               return type;
            }
         }

         return VALUES[0];
      }
   }
}
