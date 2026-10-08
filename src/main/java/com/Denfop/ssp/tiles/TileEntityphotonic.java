package com.Denfop.ssp.tiles;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;

public class TileEntityphotonic extends TileEntitySolarPanel {
   public static SolarConfig settings;

   public TileEntityphotonic() {
      super(settings);
   }
}
