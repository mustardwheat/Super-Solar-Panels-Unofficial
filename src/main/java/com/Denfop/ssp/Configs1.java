package com.Denfop.ssp;

import com.Denfop.ssp.tiles.TileEntityAdmin;
import com.Denfop.ssp.tiles.TileEntitySingular;
import com.Denfop.ssp.tiles.TileEntitySpectral;
import com.Denfop.ssp.tiles.TileEntityphotonic;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import net.minecraftforge.common.config.Configuration;

public final class Configs1 {
   private static final String GENERAL = "general";
   private static final String SOLARS = "solars";
   private static final String QUANTUM_GENERATOR = "quantum generator";
   private static final String CRAFTING = "recipes settings";
   static boolean hardRecipes;
   static boolean easyASPRecipe;
   public static boolean canCraftDoubleSlabs;
   public static boolean canCraftMT;
   public static boolean canCraftASP;
   public static boolean canCraftHSP;
   public static boolean canCraftUHSP;
   public static boolean canCraftQSP;
   public static boolean canCraftASH;
   public static boolean canCraftHSH;
   public static boolean canCraftUHSH;
   private static final String NEW_LINE = System.getProperty("line.separator");
   private static final String CONFIG_VERSION = "2.0";

   static void loadConfig(File config, boolean client) {
      SuperSolarPanels.log.info("Loading ASP Config from " + config.getAbsolutePath());
      loadNormalConfig(config, client);
   }

   private static void loadNormalConfig(File configFile, boolean client) {
      Configuration config = new Configuration(configFile);

      try {
         config.load();
         TileEntitySpectral.settings = new SolarConfig(
            config.get("solars", "SpecrtalGenDay", 32768).getInt(32768),
            config.get("solars", "SpecrtalGenNight", 20000).getInt(20000),
            config.get("solars", "SpecrtalStorage", 100000000).getInt(100000000),
            config.get("solars", "SpecrtalTier", 6).getInt(6)
         );
         TileEntitySingular.settings = new SolarConfig(
            config.get("solars", "SingularGenDay", 262144).getInt(262144),
            config.get("solars", "SingularGenNight", 196608).getInt(196608),
            config.get("solars", "SingularStorage", 1000000000).getInt(100000),
            config.get("solars", "SingularTier", 7).getInt(7)
         );
         TileEntityAdmin.settings = new SolarConfig(
            config.get("solars", "AdminGenDay", 1048576).getInt(1048576),
            config.get("solars", "AdminGenNight", 1048576).getInt(1048576),
            config.get("solars", "AdminStorage", 1000000000).getInt(1000000),
            config.get("solars", "AdminPTier", 8).getInt(8)
         );
         TileEntityphotonic.settings = new SolarConfig(
            config.get("solars", "PhotonicGenDay", 1000000000).getInt(1000000000),
            config.get("solars", "PhotonicGenNight", 1000000000).getInt(1000000000),
            config.get("solars", "PhotonicStorage", 999999999).getInt(999999999),
            config.get("solars", "PhotonicTier", 9).getInt(9)
         );
         // 各级太阳能板的最大输出（EU/t），tier 决定电压等级，maxOutput 决定实际输出上限
         TileEntitySpectral.maxOutput = config.get("solars", "SpectralMaxOutput", 32768).getInt(32768);
         TileEntitySingular.maxOutput = config.get("solars", "SingularMaxOutput", 262144).getInt(262144);
         TileEntityAdmin.maxOutput = config.get("solars", "AdminMaxOutput", 1048576).getInt(1048576);
         TileEntityphotonic.maxOutput = config.get("solars", "PhotonicMaxOutput", 1000000000).getInt(1000000000);
         canCraftDoubleSlabs = !config.get("settings Quantum chestplate", "Disable Effect FIRE RESISTANCE ", false).getBoolean(false);
         canCraftMT = !config.get("settings Quantum Boosts", "Disable Effect WATER BREATHING ", false).getBoolean(false);
         canCraftASP = !config.get("settings Quantum Boosts", "Disable Effect JUMP BOOST", false).getBoolean(false);
         canCraftASH = !config.get("settings Quantum Boosts", "Disable Effect REGENERATION ", false).getBoolean(false);
         canCraftHSP = !config.get("settings Quantum Leggins", "Disable Effect SPEED ", false).getBoolean(false);
         canCraftHSH = !config.get("settings Quantum Leggins", "Disable Effect LUCK", false).getBoolean(false);
      } catch (Exception var7) {
         SuperSolarPanels.log.fatal("Fatal error reading config file.", var7);
         throw new RuntimeException(var7);
      } finally {
         if (config.hasChanged()) {
            config.save();
         }
      }
   }

   private static void write(BufferedWriter writer, String line) throws IOException {
      writer.write(line);
      writer.newLine();
   }
}
