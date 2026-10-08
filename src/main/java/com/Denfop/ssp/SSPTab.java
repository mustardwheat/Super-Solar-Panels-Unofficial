package com.Denfop.ssp;

import com.Denfop.ssp.items.CraftingThings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * SuperSolarPanels 创造模式标签页。
 * SSP 与 ASP 的全部物品在 postInit 阶段统一归入该标签页。
 */
public final class SSPTab {
   public static final CreativeTabs TAB = new CreativeTabs("SuperSolarPanels") {
      @Override
      public ItemStack createIcon() {
         return SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.singularcore);
      }
   };

   private SSPTab() {
   }
}
