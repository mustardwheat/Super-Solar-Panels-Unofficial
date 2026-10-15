package com.denfop.ssp;

import com.chocohead.advsolar.tiles.TileEntitySolarPanel.SolarConfig;
import com.denfop.ssp.items.ItemArmourSolarHelmet;
import com.denfop.ssp.tiles.TileEntityAdmin;
import com.denfop.ssp.tiles.TileEntityNeutron;
import com.denfop.ssp.tiles.TileEntityPhotonic;
import com.denfop.ssp.tiles.TileEntitySingular;
import com.denfop.ssp.tiles.TileEntitySpectral;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

/**
 * SSP 配置加载。
 *
 * 配置注释统一使用英文：写入配置文件的注释是静态文本，不随游戏语言切换。
 * 本格式与旧版（"settings xxx" 分组、Disable Effect 反选键）不兼容，旧配置不迁移，删除后重新生成。
 *
 * @author mustardwheat
 * @date 2026/10/15
 */
public final class SspConfig {
   // 分类路径：点号是 Forge 原生嵌套分类分隔符
   private static final String GENERAL = "general";
   private static final String SPECTRAL_SOLAR = "solars.spectral";
   private static final String SINGULAR_SOLAR = "solars.singular";
   private static final String ADMIN_SOLAR = "solars.admin";
   private static final String PHOTONIC_SOLAR = "solars.photonic";
   private static final String NEUTRON_SOLAR = "solars.neutron";
   private static final String QUANTUM_HELMET = "quantum_suit.helmet";
   private static final String QUANTUM_CHESTPLATE = "quantum_suit.chestplate";
   private static final String QUANTUM_LEGGINGS = "quantum_suit.leggings";
   private static final String QUANTUM_BOOTS = "quantum_suit.boots";
   private static final String WEAPONS = "weapons";
   private static final String QUANTUM_SABER = "weapons.quantum_saber";
   private static final String SPECTRAL_SABER = "weapons.spectral_saber";

   private static final String KEY_GEN_DAY = "genDay";
   private static final String KEY_GEN_NIGHT = "genNight";
   private static final String KEY_MAX_OUTPUT = "maxOutput";
   private static final String KEY_STORAGE = "storage";
   private static final String KEY_TIER = "tier";
   private static final String KEY_MAX_CHARGE = "maxCharge";
   private static final String KEY_TRANSFER_LIMIT = "transferLimit";
   private static final String KEY_DAMAGE = "damage";

   /** 双剑伤害公式说明，写在 weapons 分类注释上 */
   private static final String COMMENT_WEAPONS = "Base damage; ×1.5 while active, ×(Sharpness level + 1) with Sharpness";

   /** 胸甲防火效果开关 */
   public static boolean effectFireResistance;
   /** 胸甲力量效果开关 */
   public static boolean effectStrength;
   /** 靴子水下呼吸效果开关 */
   public static boolean effectWaterBreathing;
   /** 靴子跳跃提升效果开关，默认关闭 */
   public static boolean effectJumpBoost;
   /** 靴子生命恢复效果开关 */
   public static boolean effectRegeneration;
   /** 护腿速度效果开关 */
   public static boolean effectSpeed;
   /** 护腿幸运效果开关 */
   public static boolean effectLuck;
   public static int saberQuantumDamage;
   public static int saberQuantumMaxCharge;
   public static int saberQuantumTransferLimit;
   public static int saberQuantumTier;
   public static int saberSpectralDamage;
   public static int saberSpectralMaxCharge;
   public static int saberSpectralTransferLimit;
   public static int saberSpectralTier;
   public static boolean saberDrainArmorOnHit;
   public static int quantumHelmetMaxCharge;
   public static int quantumHelmetTransferLimit;
   public static int quantumHelmetTier;
   public static int quantumChestplateMaxCharge;
   public static int quantumChestplateTier;
   public static int quantumLeggingsMaxCharge;
   public static int quantumLeggingsTier;
   public static int quantumBootsMaxCharge;
   public static int quantumBootsTier;

   private SspConfig() {}

   /**
    * 模组 preInit 阶段的配置入口，读取并落盘 super_solar_panels.cfg。
    */
   static void loadConfig(File config) {
      SuperSolarPanels.log.info("Loading SSP config from " + config.getAbsolutePath());
      loadNormalConfig(config);
   }

   private static void loadNormalConfig(File configFile) {
      Configuration config = new Configuration(configFile);

      try {
         config.load();
         TileEntitySpectral.settings = loadSolarConfig(config, SPECTRAL_SOLAR, 32768, 20000, 100000000, 6);
         TileEntitySpectral.maxOutput = getInt(config, SPECTRAL_SOLAR, KEY_MAX_OUTPUT, 32768);
         TileEntitySingular.settings = loadSolarConfig(config, SINGULAR_SOLAR, 262144, 196608, 1000000000, 8);
         TileEntitySingular.maxOutput = getInt(config, SINGULAR_SOLAR, KEY_MAX_OUTPUT, 262144);
         TileEntityAdmin.settings = loadSolarConfig(config, ADMIN_SOLAR, 2097152, 2097152, 1000000000, 9);
         TileEntityAdmin.maxOutput = getInt(config, ADMIN_SOLAR, KEY_MAX_OUTPUT, 2097152);
         TileEntityPhotonic.settings = loadSolarConfig(config, PHOTONIC_SOLAR, 1000000000, 1000000000, 1000000000, 10);
         TileEntityPhotonic.maxOutput = getInt(config, PHOTONIC_SOLAR, KEY_MAX_OUTPUT, 1000000000);
         // 中子太阳能拉满到 int 上限；能量等级 14 超出 ASP 量子发电机 GUI 的 1-10 档
         TileEntityNeutron.settings = loadSolarConfig(config, NEUTRON_SOLAR, Integer.MAX_VALUE, Integer.MAX_VALUE,
               Integer.MAX_VALUE, 14);
         TileEntityNeutron.maxOutput = getInt(config, NEUTRON_SOLAR, KEY_MAX_OUTPUT, Integer.MAX_VALUE);

         quantumHelmetMaxCharge = getInt(config, QUANTUM_HELMET, KEY_MAX_CHARGE, 100000000);
         quantumHelmetTransferLimit = getInt(config, QUANTUM_HELMET, KEY_TRANSFER_LIMIT, 100000);
         quantumHelmetTier = getInt(config, QUANTUM_HELMET, KEY_TIER, 8);
         // 胸甲/护腿/靴子的充放电速率固定 100000 EU/t，未开放配置
         quantumChestplateMaxCharge = getInt(config, QUANTUM_CHESTPLATE, KEY_MAX_CHARGE, 100000000);
         quantumChestplateTier = getInt(config, QUANTUM_CHESTPLATE, KEY_TIER, 8);
         quantumLeggingsMaxCharge = getInt(config, QUANTUM_LEGGINGS, KEY_MAX_CHARGE, 100000000);
         quantumLeggingsTier = getInt(config, QUANTUM_LEGGINGS, KEY_TIER, 8);
         quantumBootsMaxCharge = getInt(config, QUANTUM_BOOTS, KEY_MAX_CHARGE, 100000000);
         quantumBootsTier = getInt(config, QUANTUM_BOOTS, KEY_TIER, 8);

         effectFireResistance = getBoolean(config, QUANTUM_CHESTPLATE, "enableFireResistance", true);
         effectStrength = getBoolean(config, QUANTUM_CHESTPLATE, "enableStrength", true);
         effectSpeed = getBoolean(config, QUANTUM_LEGGINGS, "enableSpeed", true);
         effectLuck = getBoolean(config, QUANTUM_LEGGINGS, "enableLuck", true);
         effectWaterBreathing = getBoolean(config, QUANTUM_BOOTS, "enableWaterBreathing", true);
         effectJumpBoost = getBoolean(config, QUANTUM_BOOTS, "enableJumpBoost", false);
         effectRegeneration = getBoolean(config, QUANTUM_BOOTS, "enableRegeneration", true);

         // 对齐 ASP 混合/终混太阳能头盔的背包充电行为
         ItemArmourSolarHelmet.chargeWholeInventory = config.get(GENERAL, "solarHelmetsChargeWholeInventory", true,
               "Solar helmets charge every item in the inventory").getBoolean(true);
         saberDrainArmorOnHit = config.get(GENERAL, "saberDrainArmorOnHit", true,
               "Sabers drain and strip the target's armor on hit").getBoolean(true);

         config.addCustomCategoryComment(WEAPONS, COMMENT_WEAPONS);
         saberQuantumDamage = getInt(config, QUANTUM_SABER, KEY_DAMAGE, 25);
         saberQuantumMaxCharge = getInt(config, QUANTUM_SABER, KEY_MAX_CHARGE, 10000000);
         saberQuantumTransferLimit = getInt(config, QUANTUM_SABER, KEY_TRANSFER_LIMIT, 2048);
         saberQuantumTier = getInt(config, QUANTUM_SABER, KEY_TIER, 4);
         saberSpectralDamage = getInt(config, SPECTRAL_SABER, KEY_DAMAGE, 40);
         saberSpectralMaxCharge = getInt(config, SPECTRAL_SABER, KEY_MAX_CHARGE, 100000000);
         saberSpectralTransferLimit = getInt(config, SPECTRAL_SABER, KEY_TRANSFER_LIMIT, 2048);
         saberSpectralTier = getInt(config, SPECTRAL_SABER, KEY_TIER, 5);
      } catch (Exception e) {
         SuperSolarPanels.log.fatal("Fatal error reading config file.", e);
         throw new RuntimeException(e);
      } finally {
         if (config.hasChanged()) {
            config.save();
         }
      }
   }

   /**
    * 读取一个太阳能板分类，返回与 ASP 一致的 SolarConfig；maxOutput 不在 SolarConfig 内，需单独读取。
    */
   private static SolarConfig loadSolarConfig(Configuration config, String category, int genDay, int genNight,
         int storage, int tier) {
      return new SolarConfig(
            getInt(config, category, KEY_GEN_DAY, genDay),
            getInt(config, category, KEY_GEN_NIGHT, genNight),
            getInt(config, category, KEY_STORAGE, storage),
            getInt(config, category, KEY_TIER, tier)
      );
   }

   private static int getInt(Configuration config, String category, String key, int defaultValue) {
      return config.get(category, key, defaultValue).getInt(defaultValue);
   }

   private static boolean getBoolean(Configuration config, String category, String key, boolean defaultValue) {
      return config.get(category, key, defaultValue).getBoolean(defaultValue);
   }

}
