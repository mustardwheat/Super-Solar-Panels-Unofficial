package com.denfop.ssp;

import com.chocohead.advsolar.tiles.TEs;
import com.denfop.ssp.tiles.SSPBlock;
import ic2.core.block.TeBlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * 创造模式标签页：ASP 与 SSP 各一页。
 * 两 mod 的物品与机器方块在 postInit 阶段按注册命名空间分别归入对应标签页
 * （见 SuperSolarPanels#postInit），机器子物品由 SSPCreativeRegisterer 输出。
 */
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
