package com.denfop.ssp.tiles;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;
import ic2.core.init.Localization;

public class TileEntityNeutron extends TileEntitySolarPanel {
   public static SolarConfig settings;
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
      if (this.maxStorage - this.storage < amount) {
         this.storage = this.maxStorage;
      } else {
         this.storage += amount;
      }
   }
}
