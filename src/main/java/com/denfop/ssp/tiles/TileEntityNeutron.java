package com.denfop.ssp.tiles;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;
import ic2.core.init.Localization;

public class TileEntityNeutron extends TileEntitySolarPanel {
   public static SolarConfig settings;
   /** 最大输出功率（EU/t），由配置文件读取 */
   public static int maxOutput;

   public TileEntityNeutron() {
      super(settings);
   }

   @Override
   public double getOfferedEnergy() {
      return Math.min(this.storage, maxOutput);
   }

   @Override
   public String getMaxOutput() {
      return String.format(
         "%s %d %s",
         Localization.translate("advanced_solar_panels.gui.maxOutput"),
         maxOutput,
         Localization.translate("ic2.generic.text.EUt")
      );
   }

   @Override
   public void tryGenerateEnergy(int amount) {
      // 储能与发电量均为 Integer.MAX_VALUE，直接相加会 int 溢出成负数，改为按剩余容量截断
      if (this.maxStorage - this.storage < amount) {
         this.storage = this.maxStorage;
      } else {
         this.storage += amount;
      }
   }
}
