package com.denfop.ssp;

import com.chocohead.advsolar.tiles.TEs;
import com.denfop.ssp.tiles.SSPBlock;
import ic2.core.block.TeBlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public final class SSPTab {
   public static final CreativeTabs ASP_TAB = new CreativeTabs("AdvancedSolarPanels") {
      @Override
      public ItemStack createIcon() {
         return TeBlockRegistry.get(TEs.IDENTITY).getItemStack(TEs.advanced_solar_panel);
      }
   };

   public static final CreativeTabs TAB = new CreativeTabs("SuperSolarPanels") {
      @Override
      public ItemStack createIcon() {
         return SuperSolarPanels.machines.getItemStack(SSPBlock.admin_solar_panel);
      }
   };

   private SSPTab() {
   }
}
