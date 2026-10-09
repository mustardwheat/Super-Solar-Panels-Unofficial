package com.denfop.ssp.tiles;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;
import ic2.core.init.Localization;

public class TileEntityPhotonic extends TileEntitySolarPanel {
   public static SolarConfig settings;
   /** 最大输出功率（EU/t），由配置文件读取 */
   public static int maxOutput;

   public TileEntityPhotonic() {
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
}
