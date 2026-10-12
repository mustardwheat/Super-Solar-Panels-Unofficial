package com.denfop.ssp;

import com.denfop.ssp.tiles.TileEntityAdmin;
import com.denfop.ssp.items.ItemArmourSolarHelmet;
import com.denfop.ssp.tiles.TileEntityNeutron;
import com.denfop.ssp.tiles.TileEntitySingular;
import com.denfop.ssp.tiles.TileEntitySpectral;
import com.denfop.ssp.tiles.TileEntityPhotonic;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

public final class SspConfig {
   private static final String SPECTRAL_SOLAR = "settings spectral solar panel";
   private static final String SINGULAR_SOLAR = "settings singular solar panel";
   private static final String ADMIN_SOLAR = "settings admin solar panel";
   private static final String PHOTONIC_SOLAR = "settings photonic solar panel";
   private static final String NEUTRON_SOLAR = "settings neutron solar panel";
   public static boolean canCraftDoubleSlabs;
   public static boolean canCraftMT;
   public static boolean canCraftASP;
   public static boolean canCraftHSP;
   public static boolean canCraftASH;
   public static boolean canCraftHSH;
   public static int saberQuantumDamage;
   public static int saberQuantumMaxCharge;
   public static int saberQuantumTransferLimit;
   public static int saberQuantumTier;
   public static int saberSpectralDamage;
   public static int saberSpectralMaxCharge;
   public static int saberSpectralTransferLimit;
   public static int saberSpectralTier;
   public static boolean saberDrainArmorOnHit;
   public static int twelveHeatStorage;
   public static int maxHeatStorage;
   public static int quantumHelmetMaxCharge;
   public static int quantumHelmetTransferLimit;
   public static int quantumHelmetTier;
   public static int quantumChestplateMaxCharge;
   public static int quantumLegginsMaxCharge;
   public static int quantumBoostsMaxCharge;

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
            config.get(ADMIN_SOLAR, "GenDay", 2097152).getInt(2097152),
            config.get(ADMIN_SOLAR, "GenNight", 2097152).getInt(2097152),
            config.get(ADMIN_SOLAR, "Storage", 1000000000).getInt(1000000000),
            config.get(ADMIN_SOLAR, "Tier", 8).getInt(8)
         );
         TileEntityAdmin.maxOutput = config.get(ADMIN_SOLAR, "MaxOutput", 2097152).getInt(2097152);
         TileEntityPhotonic.settings = new SolarConfig(
            config.get(PHOTONIC_SOLAR, "GenDay", 1000000000).getInt(1000000000),
            config.get(PHOTONIC_SOLAR, "GenNight", 1000000000).getInt(1000000000),
            config.get(PHOTONIC_SOLAR, "Storage", 1000000000).getInt(1000000000),
            config.get(PHOTONIC_SOLAR, "Tier", 9).getInt(9)
         );
         TileEntityPhotonic.maxOutput = config.get(PHOTONIC_SOLAR, "MaxOutput", 1000000000).getInt(1000000000);
         // 中子太阳能：发电与储能拉满到 int 上限，能量等级 14（单包电压 2^31，超出 ASP 量子发电机 GUI 的 1-10 档）
         TileEntityNeutron.settings = new SolarConfig(
            config.get(NEUTRON_SOLAR, "GenDay", 2147483647).getInt(2147483647),
            config.get(NEUTRON_SOLAR, "GenNight", 2147483647).getInt(2147483647),
            config.get(NEUTRON_SOLAR, "Storage", 2147483647).getInt(2147483647),
            config.get(NEUTRON_SOLAR, "Tier", 14).getInt(14)
         );
         TileEntityNeutron.maxOutput = config.get(NEUTRON_SOLAR, "MaxOutput", 2147483647).getInt(2147483647);
         canCraftDoubleSlabs = !config.get("settings Quantum chestplate", "Disable Effect FIRE RESISTANCE ", false).getBoolean(false);
         canCraftMT = !config.get("settings Quantum Boosts", "Disable Effect WATER BREATHING ", false).getBoolean(false);
         canCraftASP = !config.get("settings Quantum Boosts", "Disable Effect JUMP BOOST", false).getBoolean(false);
         canCraftASH = !config.get("settings Quantum Boosts", "Disable Effect REGENERATION ", false).getBoolean(false);
         canCraftHSP = !config.get("settings Quantum Leggins", "Disable Effect SPEED ", false).getBoolean(false);
         canCraftHSH = !config.get("settings Quantum Leggins", "Disable Effect LUCK", false).getBoolean(false);
         // 太阳能头盔是否给整个背包充电（默认开启，对齐 ASP 混合/终混太阳能头盔行为）
         ItemArmourSolarHelmet.chargeWholeInventory = config.get("general", "solarHelmetsChargeWholeInventory", true).getBoolean(true);
         // 双剑命中时抽干目标纳米/量子护甲电量并扒掉护甲件的机制开关（默认开启）
         saberDrainArmorOnHit = config.get("general", "saberDrainArmorOnHit", true).getBoolean(true);
         // 量子剑/光谱剑常态伤害；激活态 ×1.5，有锋利附魔时 ×(锋利等级 + 1)，与 METS 量子剑乘伤一致
         saberQuantumDamage = config.get("settings quantum saber", "Damage", 25).getInt(25);
         saberQuantumMaxCharge = config.get("settings quantum saber", "maxCharge", 10000000).getInt(10000000);
         saberQuantumTransferLimit = config.get("settings quantum saber", "transferLimit", 2048).getInt(2048);
         saberQuantumTier = config.get("settings quantum saber", "tier", 4).getInt(4);
         saberSpectralDamage = config.get("settings spectral saber", "Damage", 40).getInt(40);
         saberSpectralMaxCharge = config.get("settings spectral saber", "maxCharge", 100000000).getInt(100000000);
         saberSpectralTransferLimit = config.get("settings spectral saber", "transferLimit", 2000).getInt(2000);
         saberSpectralTier = config.get("settings spectral saber", "tier", 5).getInt(5);
         // 冷却单元热容量（默认值与 1.4 保持一致）
         twelveHeatStorage = config.get("settings twelve heat storage", "heatStorage", 120000).getInt(120000);
         maxHeatStorage = config.get("settings max heat storage", "heatStorage", 240000).getInt(240000);
         // 强化量子头盔数值（默认值与 1.4 保持一致）
         quantumHelmetMaxCharge = config.get("settings quantum helmet", "maxCharge", 100000000).getInt(100000000);
         quantumHelmetTransferLimit = config.get("settings quantum helmet", "transferLimit", 100000).getInt(100000);
         quantumHelmetTier = config.get("settings quantum helmet", "tier", 8).getInt(8);
         // 强化量子胸甲/护腿/靴子最大储能，默认与强化量子头盔一致（1 亿 EU）
         quantumChestplateMaxCharge = config.get("settings quantum chestplate", "maxCharge", 100000000).getInt(100000000);
         quantumLegginsMaxCharge = config.get("settings quantum leggins", "maxCharge", 100000000).getInt(100000000);
         quantumBoostsMaxCharge = config.get("settings quantum boosts", "maxCharge", 100000000).getInt(100000000);
      } catch (Exception var7) {
         SuperSolarPanels.log.fatal("Fatal error reading config file.", var7);
         throw new RuntimeException(var7);
      } finally {
         if (config.hasChanged()) {
            config.save();
         }
      }
   }

}
