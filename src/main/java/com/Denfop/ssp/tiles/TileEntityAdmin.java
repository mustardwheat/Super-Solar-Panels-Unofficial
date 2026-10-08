package com.Denfop.ssp.tiles;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;

public class TileEntityAdmin extends TileEntitySolarPanel {
   public static SolarConfig settings;

   public TileEntityAdmin() {
      super(settings);
   }
}
