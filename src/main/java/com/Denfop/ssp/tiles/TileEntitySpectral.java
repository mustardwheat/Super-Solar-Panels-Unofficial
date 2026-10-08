package com.Denfop.ssp.tiles;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;

public class TileEntitySpectral extends TileEntitySolarPanel {
   public static SolarConfig settings;

   public TileEntitySpectral() {
      super(settings);
   }
}
