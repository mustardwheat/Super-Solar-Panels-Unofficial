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
   private static final String SPECTRAL_SOLAR = "settings spectral solar panel";
   private static final String SINGULAR_SOLAR = "settings singular solar panel";
   private static final String ADMIN_SOLAR = "settings admin solar panel";
   private static final String PHOTONIC_SOLAR = "settings photonic solar panel";
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
   public static int saberQuantumDamage;
   public static int saberQuantumActiveDamage;
   public static int saberQuantumMaxCharge;
   public static int saberQuantumTransferLimit;
   public static int saberQuantumTier;
   public static int saberSpectralDamage;
   public static int saberSpectralActiveDamage;
   public static int saberSpectralMaxCharge;
   public static int saberSpectralTransferLimit;
   public static int saberSpectralTier;
   public static int twelveHeatStorage;
   public static int maxHeatStorage;
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
         // 每种太阳能板一个配置组，键名与其他物品的配置组保持一致
         TileEntitySpectral.settings = new SolarConfig(
            config.get(SPECTRAL_SOLAR, "GenDay", 32768).getInt(32768),
            config.get(SPECTRAL_SOLAR, "GenNight", 20000).getInt(20000),
            config.get(SPECTRAL_SOLAR, "Storage", 100000000).getInt(100000000),
            config.get(SPECTRAL_SOLAR, "Tier", 6).getInt(6)
         );
         TileEntitySpectral.maxOutput = config.get(SPECTRAL_SOLAR, "MaxOutput", 32768).getInt(32768);
         TileEntitySingular.settings = new SolarConfig(
            config.get(SINGULAR_SOLAR, "GenDay", 262144).getInt(262144),
            config.get(SINGULAR_SOLAR, "GenNight", 196608).getInt(196608),
            config.get(SINGULAR_SOLAR, "Storage", 1000000000).getInt(1000000000),
            config.get(SINGULAR_SOLAR, "Tier", 7).getInt(7)
         );
         TileEntitySingular.maxOutput = config.get(SINGULAR_SOLAR, "MaxOutput", 262144).getInt(262144);
         TileEntityAdmin.settings = new SolarConfig(
            config.get(ADMIN_SOLAR, "GenDay", 1048576).getInt(1048576),
            config.get(ADMIN_SOLAR, "GenNight", 1048576).getInt(1048576),
            config.get(ADMIN_SOLAR, "Storage", 1000000000).getInt(1000000000),
            config.get(ADMIN_SOLAR, "Tier", 8).getInt(8)
         );
         TileEntityAdmin.maxOutput = config.get(ADMIN_SOLAR, "MaxOutput", 1048576).getInt(1048576);
         TileEntityphotonic.settings = new SolarConfig(
            config.get(PHOTONIC_SOLAR, "GenDay", 1000000000).getInt(1000000000),
            config.get(PHOTONIC_SOLAR, "GenNight", 1000000000).getInt(1000000000),
            config.get(PHOTONIC_SOLAR, "Storage", 999999999).getInt(999999999),
            config.get(PHOTONIC_SOLAR, "Tier", 9).getInt(9)
         );
         TileEntityphotonic.maxOutput = config.get(PHOTONIC_SOLAR, "MaxOutput", 1000000000).getInt(1000000000);
         canCraftDoubleSlabs = !config.get("settings Quantum chestplate", "Disable Effect FIRE RESISTANCE ", false).getBoolean(false);
         canCraftMT = !config.get("settings Quantum Boosts", "Disable Effect WATER BREATHING ", false).getBoolean(false);
         canCraftASP = !config.get("settings Quantum Boosts", "Disable Effect JUMP BOOST", false).getBoolean(false);
         canCraftASH = !config.get("settings Quantum Boosts", "Disable Effect REGENERATION ", false).getBoolean(false);
         canCraftHSP = !config.get("settings Quantum Leggins", "Disable Effect SPEED ", false).getBoolean(false);
         canCraftHSH = !config.get("settings Quantum Leggins", "Disable Effect LUCK", false).getBoolean(false);
         // 量子剑/光谱剑数值（默认值与 1.4 保持一致）
         saberQuantumDamage = config.get("settings quantum saber", "Damage", 11).getInt(11);
         saberQuantumActiveDamage = config.get("settings quantum saber", "ActiveDamage", 29).getInt(29);
         saberQuantumMaxCharge = config.get("settings quantum saber", "maxCharge", 300000).getInt(300000);
         saberQuantumTransferLimit = config.get("settings quantum saber", "transferLimit", 2000).getInt(2000);
         saberQuantumTier = config.get("settings quantum saber", "tier", 4).getInt(4);
         saberSpectralDamage = config.get("settings spectral saber", "Damage", 14).getInt(14);
         saberSpectralActiveDamage = config.get("settings spectral saber", "ActiveDamage", 39).getInt(39);
         saberSpectralMaxCharge = config.get("settings spectral saber", "maxCharge", 600000).getInt(600000);
         saberSpectralTransferLimit = config.get("settings spectral saber", "transferLimit", 2000).getInt(2000);
         saberSpectralTier = config.get("settings spectral saber", "tier", 5).getInt(5);
         // 冷却单元热容量（默认值与 1.4 保持一致）
         twelveHeatStorage = config.get("settings twelve heat storage", "heatStorage", 120000).getInt(120000);
         maxHeatStorage = config.get("settings max heat storage", "heatStorage", 240000).getInt(240000);
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
