package com.Denfop.ssp.tiles;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;

public class TileEntitySingular extends TileEntitySolarPanel {
   public static SolarConfig settings;

   public TileEntitySingular() {
      super(settings);
   }
}
