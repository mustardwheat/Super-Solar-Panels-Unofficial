package com.denfop.ssp.items;

import com.denfop.ssp.Configs1;
import com.google.common.base.CaseFormat;
import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudProvider;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.init.BlocksItems;
import ic2.core.init.Localization;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.ItemTinCan;
import ic2.core.ref.IItemModelProvider;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 强化量子头盔，移植自 1.4 的 ItemArmorQuantumHelmet。
 * 功能：耗电清除负面药水效果、水下补气、自动进食罐头、夜视开关与 HUD 模式切换。
 * 与太阳能头盔的区别：不发电，因此移除了 1.4 中发电量恒为 0 的充能分配死代码。
 */
public class ItemArmorQuantumHelmet extends ItemArmor implements IItemModelProvider, IElectricItem, IMetalArmor, ISpecialArmor, IItemHudProvider {
   /** 各负面药水对应的清除耗电量（EU），数值与 1.4 保持一致 */
   protected static final Map<Potion, Integer> POTION_REMOVAL_COST = new IdentityHashMap<>();
   protected final ItemArmorQuantumHelmet.SolarHelmetTypes type;

   static {
      POTION_REMOVAL_COST.put(IC2Potion.radiation, 5000);
      POTION_REMOVAL_COST.put(MobEffects.POISON, 400);
      POTION_REMOVAL_COST.put(MobEffects.WITHER, 500);
      POTION_REMOVAL_COST.put(MobEffects.SLOWNESS, 300);
      POTION_REMOVAL_COST.put(MobEffects.NAUSEA, 1000);
      POTION_REMOVAL_COST.put(MobEffects.HUNGER, 300);
      POTION_REMOVAL_COST.put(MobEffects.WEAKNESS, 400);
   }

   public ItemArmorQuantumHelmet(ItemArmorQuantumHelmet.SolarHelmetTypes type) {
      super(ArmorMaterial.DIAMOND, -1, EntityEquipmentSlot.HEAD);
      ((ItemArmorQuantumHelmet)BlocksItems.registerItem(this, new ResourceLocation("super_solar_panels", type.getName())))
         .setTranslationKey(type.getLocalisedName());
      this.setMaxDamage(27);
      this.type = type;
   }

   public String getTranslationKey() {
      return "super_solar_panels." + super.getTranslationKey().substring(5);
   }

   public String getTranslationKey(ItemStack stack) {
      return this.getTranslationKey();
   }

   public String getItemStackDisplayName(ItemStack stack) {
      return Localization.translate(this.getTranslationKey(stack));
   }

   public int getMetadata(ItemStack stack) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public void registerModels(ItemName name) {
      ModelLoader.setCustomModelResourceLocation(
         this, 0, new ModelResourceLocation("super_solar_panels:" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.type.getName()), (String)null)
      );
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return "super_solar_panels:textures/armour/" + this.type.getName() + (type != null ? "Overlay" : "") + ".png";
   }

   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
      if (!this.HUDstuff(world.isRemote, player, stack)) {
         NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
         byte toggleTimer = nbtData.getByte("toggleTimer");
         // 水下补气：空气不足时耗电回填
         int air = player.getAir();
         if (ElectricItem.manager.canUse(stack, 1000.0) && air < 100) {
            player.setAir(air + 200);
            ElectricItem.manager.use(stack, 1000.0, null);
         } else if (air <= 0) {
            IC2.achievements.issueAchievement(player, "starveWithQHelmet");
         }

         // 自动进食：饥饿时耗电食用背包中的灌装食物
         if (ElectricItem.manager.canUse(stack, 1000.0) && player.getFoodStats().needFood()) {
            int slot = -1;

            for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
               ItemStack playerStack = (ItemStack)player.inventory.mainInventory.get(i);
               if (!StackUtil.isEmpty(playerStack) && playerStack.getItem() == ItemName.filled_tin_can.getInstance()) {
                  slot = i;
                  break;
               }
            }

            if (slot > -1) {
               ItemStack playerStack2 = (ItemStack)player.inventory.mainInventory.get(slot);
               ItemTinCan can = (ItemTinCan)playerStack2.getItem();
               ActionResult<ItemStack> result = can.onEaten(player, playerStack2);
               playerStack2 = (ItemStack)result.getResult();
               if (StackUtil.isEmpty(playerStack2)) {
                  player.inventory.mainInventory.set(slot, StackUtil.emptyStack);
               }

               if (result.getType() == EnumActionResult.SUCCESS) {
                  ElectricItem.manager.use(stack, 1000.0, null);
               }
            }
         } else if (player.getFoodStats().getFoodLevel() <= 0) {
            IC2.achievements.issueAchievement(player, "starveWithQHelmet");
         }

         // 耗电清除负面药水效果，倍率随药水等级放大
         for (PotionEffect effect : new LinkedList<>(player.getActivePotionEffects())) {
            Potion potion = effect.getPotion();
            Integer cost = POTION_REMOVAL_COST.get(potion);
            if (cost != null) {
               cost = cost * Math.max(1, effect.getAmplifier() + 1);
               if (ElectricItem.manager.canUse(stack, (double)cost)) {
                  ElectricItem.manager.use(stack, (double)cost, null);
                  IC2.platform.removePotion(player, potion);
               }
            }
         }

         // 夜视开关：Alt + 模式切换键，与太阳能头盔按键方案保持一致
         boolean Nightvision = nbtData.getBoolean("Nightvision");
         short hubmode = nbtData.getShort("HudMode");
         if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
            toggleTimer = 10;
            Nightvision = !Nightvision;
            if (IC2.platform.isSimulating()) {
               nbtData.setBoolean("Nightvision", Nightvision);
               if (Nightvision) {
                  IC2.platform.messagePlayer(player, "Nightvision enabled.", new Object[0]);
               } else {
                  IC2.platform.messagePlayer(player, "Nightvision disabled.", new Object[0]);
               }
            }
         }

         if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
            toggleTimer = 10;
            if (hubmode == HudMode.getMaxMode()) {
               hubmode = 0;
            } else {
               hubmode++;
            }

            if (IC2.platform.isSimulating()) {
               nbtData.setShort("HudMode", hubmode);
               IC2.platform.messagePlayer(player, Localization.translate(HudMode.getFromID(hubmode).getTranslationKey()), new Object[0]);
            }
         }

         if (IC2.platform.isSimulating() && toggleTimer > 0) {
            nbtData.setByte("toggleTimer", --toggleTimer);
         }

         // 夜视仅在环境亮度足够低时施加，逻辑与 1.4 保持一致
         if (Nightvision && IC2.platform.isSimulating() && ElectricItem.manager.use(stack, 1.0, player)) {
            BlockPos pos = new BlockPos((int)Math.floor(player.posX), (int)Math.floor(player.posY), (int)Math.floor(player.posZ));
            int skylight = player.getEntityWorld().getLightFromNeighbors(pos);
            if (skylight > 8) {
               player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
            } else {
               player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, true, true));
            }
         }
      }
   }

   protected boolean HUDstuff(boolean isRemote, EntityPlayer player, ItemStack stack) {
      NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
      byte toggleTimer = nbt.getByte("toggleTimer");
      if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
         byte hubmode = nbt.getByte("hudMode");
         toggleTimer = 10;
         if (hubmode == HudMode.getMaxMode()) {
            hubmode = 0;
         } else {
            hubmode++;
         }

         if (!isRemote) {
            nbt.setByte("hudMode", hubmode);
            IC2.platform.messagePlayer(player, Localization.translate(HudMode.getFromID(hubmode).getTranslationKey()), new Object[0]);
         }
      }

      if (!isRemote && toggleTimer > 0) {
         nbt.setByte("toggleTimer", --toggleTimer);
      }

      return isRemote;
   }

   public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
      if (this.isInCreativeTab(tab)) {
         ElectricItemManager.addChargeVariants(this, items);
      }
   }

   public EnumRarity getRarity(ItemStack stack) {
      return this.type.rarity;
   }

   public boolean isMetalArmor(ItemStack stack, EntityPlayer player) {
      return true;
   }

   public int getItemEnchantability() {
      return 0;
   }

   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
      return false;
   }

   public ArmorProperties getProperties(EntityLivingBase player, ItemStack armour, DamageSource source, double damage, int slot) {
      return source.isUnblockable()
         ? new ArmorProperties(0, 0.0, 0)
         : new ArmorProperties(0, 0.15 * this.type.damageAbsorptionRatio, (int)(25.0 * ElectricItem.manager.getCharge(armour) / this.type.energyPerDamage));
   }

   public int getArmorDisplay(EntityPlayer player, ItemStack armour, int slot) {
      return ElectricItem.manager.getCharge(armour) >= this.type.energyPerDamage ? (int)Math.round(3.0 * this.type.damageAbsorptionRatio) : 0;
   }

   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
      ElectricItem.manager.discharge(stack, damage * this.type.energyPerDamage, Integer.MAX_VALUE, true, false, false);
   }

   public boolean canProvideEnergy(ItemStack stack) {
      return false;
   }

   public int getTier(ItemStack stack) {
      return this.type.tier;
   }

   public double getMaxCharge(ItemStack stack) {
      return this.type.maxCharge;
   }

   public double getTransferLimit(ItemStack stack) {
      return this.type.transferLimit;
   }

   public boolean doesProvideHUD(ItemStack stack) {
      return ElectricItem.manager.getCharge(stack) > 0.0;
   }

   public HudMode getHudMode(ItemStack stack) {
      return HudMode.getFromID(StackUtil.getOrCreateNbtData(stack).getByte("hudMode"));
   }

   public static enum SolarHelmetTypes {
      Helmet(Configs1.quantumHelmetTier, Configs1.quantumHelmetMaxCharge, Configs1.quantumHelmetTransferLimit);

      public final double maxCharge;
      public final double transferLimit;
      public final double damageAbsorptionRatio;
      public final int tier;
      public final int energyPerDamage;
      public final EnumRarity rarity;
      private final String name = this.name().toLowerCase(Locale.ENGLISH);

      private SolarHelmetTypes(int tier, double maxCharge, double transferLimit) {
         this.rarity = EnumRarity.EPIC;
         this.tier = tier;
         this.maxCharge = maxCharge;
         this.transferLimit = transferLimit;
         // 固定数值与 1.4 保持一致：每点伤害耗能 42000 EU，伤害吸收倍率 9
         this.energyPerDamage = 42000;
         this.damageAbsorptionRatio = 9.0;

         assert this.damageAbsorptionRatio > 0.0;
      }

      public String getName() {
         return this.name + "SolarHelmet";
      }

      protected String getLocalisedName() {
         return "solar_helmets." + this.name;
      }
   }
}
