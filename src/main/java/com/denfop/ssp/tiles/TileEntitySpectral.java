package com.denfop.ssp.tiles;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;
import ic2.core.init.Localization;

public class TileEntitySpectral extends TileEntitySolarPanel {
   public static SolarConfig settings;
   public static int maxOutput;

   public TileEntitySpectral() {
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
