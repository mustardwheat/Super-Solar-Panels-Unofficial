package com.Denfop.ssp.items;

import com.Denfop.ssp.tiles.TileEntitySingular;
import com.Denfop.ssp.tiles.TileEntitySpectral;
import com.chocohead.advsolar.tiles.TileEntitySolarPanel.GenerationState;
import com.google.common.base.CaseFormat;
import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudProvider;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.init.BlocksItems;
import ic2.core.init.Localization;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.ItemTinCan;
import ic2.core.ref.IItemModelProvider;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.LinkedList;
import java.util.Locale;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
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

public class ItemArmourSolarHelmet extends ItemArmor implements IItemModelProvider, IElectricItem, IMetalArmor, ISpecialArmor, IItemHudProvider {
   protected static final int DEFAULT_COLOUR = -1;
   protected final ItemArmourSolarHelmet.SolarHelmetTypes type;
   public static boolean chargeWholeInventory = false;
   protected GenerationState state;
   protected int ticker;

   public ItemArmourSolarHelmet(ItemArmourSolarHelmet.SolarHelmetTypes type) {
      super(ArmorMaterial.DIAMOND, -1, EntityEquipmentSlot.HEAD);
      ((ItemArmourSolarHelmet)BlocksItems.registerItem(this, new ResourceLocation("super_solar_panels", type.getName())))
         .setTranslationKey(type.getLocalisedName());
      this.setCreativeTab(IC2.tabIC2);
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

   public boolean canBeDyed() {
      return this.type != ItemArmourSolarHelmet.SolarHelmetTypes.Spectral;
   }

   public void setColor(ItemStack stack, int colour) {
      this.getDisplayNbt(stack, true).setInteger("colour", colour);
   }

   public boolean hasColor(ItemStack stack) {
      return this.getColor(stack) != -1;
   }

   public int getColor(ItemStack stack) {
      NBTTagCompound nbt = this.getDisplayNbt(stack, false);
      return nbt != null && nbt.hasKey("colour", 3) ? nbt.getInteger("colour") : -1;
   }

   public void removeColor(ItemStack stack) {
      NBTTagCompound nbt = this.getDisplayNbt(stack, false);
      if (nbt != null && nbt.hasKey("colour", 3)) {
         nbt.removeTag("colour");
         if (nbt.isEmpty()) {
            stack.getTagCompound().removeTag("display");
         }
      }
   }

   protected NBTTagCompound getDisplayNbt(ItemStack stack, boolean create) {
      NBTTagCompound nbt = stack.getTagCompound();
      if (nbt == null) {
         if (!create) {
            return null;
         }

         nbt = new NBTTagCompound();
         stack.setTagCompound(nbt);
      }

      NBTTagCompound out;
      if (!nbt.hasKey("display", 50)) {
         if (!create) {
            return null;
         }

         out = new NBTTagCompound();
         nbt.setTag("display", out);
      } else {
         out = nbt.getCompoundTag("display");
      }

      return out;
   }

   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
      if (!this.HUDstuff(world.isRemote, player, stack)) {
         if (this.ticker++ % this.tickRate() == 0) {
            this.checkTheSky(world, player.getPosition());
         }

         if (this.type != ItemArmourSolarHelmet.SolarHelmetTypes.Spectral) {
            int airLevel = player.getAir();
            if (ElectricItem.manager.canUse(stack, 1000.0) && airLevel < 100) {
               player.setAir(airLevel + 200);
               ElectricItem.manager.use(stack, 1000.0, player);
            }
         }

         int output = 0;
         switch (this.state) {
            case DAY:
               output = this.type.dayEU;
               break;
            case NIGHT:
               output = this.type.nightEU;
               break;
            default:
               return;
         }

         for (ItemStack playerStack : player.inventory.armorInventory.subList(0, player.inventory.armorInventory.size() - 1)) {
            if (!StackUtil.isEmpty(playerStack) && playerStack.getItem() instanceof IElectricItem) {
               output -= (int)ElectricItem.manager.charge(playerStack, output, this.type.tier, false, false);
               if (output <= 0) {
                  return;
               }
            }
         }

         if (chargeWholeInventory) {
            for (ItemStack playerStackx : player.inventory.offHandInventory) {
               if (!StackUtil.isEmpty(playerStackx) && playerStackx.getItem() instanceof IElectricItem) {
                  output -= (int)ElectricItem.manager.charge(playerStackx, output, this.type.tier, false, false);
                  if (output <= 0) {
                     return;
                  }
               }
            }

            for (ItemStack playerStackxx : player.inventory.mainInventory) {
               if (!StackUtil.isEmpty(playerStackxx) && playerStackxx.getItem() instanceof IElectricItem) {
                  output -= (int)ElectricItem.manager.charge(playerStackxx, output, this.type.tier, false, false);
                  if (output <= 0) {
                     return;
                  }
               }
            }
         }

         NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
         byte toggleTimer = nbtData.getByte("toggleTimer");
         boolean ret = false;
         ElectricItem.manager.charge(stack, output, Integer.MAX_VALUE, true, false);
         int air = player.getAir();
         if (ElectricItem.manager.canUse(stack, 1000.0) && air < 100) {
            player.setAir(air + 200);
            ElectricItem.manager.use(stack, 1000.0, null);
            ret = true;
         } else if (air <= 0) {
            IC2.achievements.issueAchievement(player, "starveWithQHelmet");
         }

         if (ElectricItem.manager.canUse(stack, 1000.0) && player.getFoodStats().needFood()) {
            int slot = -1;

            for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
               ItemStack playerStackxxx = (ItemStack)player.inventory.mainInventory.get(i);
               if (!StackUtil.isEmpty(playerStackxxx) && playerStackxxx.getItem() == ItemName.filled_tin_can.getInstance()) {
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

               ret = true;
            }
         } else if (player.getFoodStats().getFoodLevel() <= 0) {
            IC2.achievements.issueAchievement(player, "starveWithQHelmet");
         }

         for (Object effect : new LinkedList(player.getActivePotionEffects())) {
            Potion var33 = ((PotionEffect)effect).getPotion();
         }

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
            String s = "toggleTimer";
            nbtData.setByte("toggleTimer", --toggleTimer);
         }

         if (Nightvision && IC2.platform.isSimulating() && ElectricItem.manager.use(stack, 1.0, player)) {
            BlockPos pos = new BlockPos((int)Math.floor(player.posX), (int)Math.floor(player.posY), (int)Math.floor(player.posZ));
            int skylight = player.getEntityWorld().getLightFromNeighbors(pos);
            if (skylight > 8) {
               IC2.platform.removePotion(player, MobEffects.NIGHT_VISION);
               player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0, true, true));
            } else {
               IC2.platform.removePotion(player, MobEffects.BLINDNESS);
               player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, true, true));
            }

            ret = true;
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
         String s = "toggleTimer";
         nbt.setByte("toggleTimer", --toggleTimer);
      }

      return isRemote;
   }

   protected int tickRate() {
      return 128;
   }

   public void checkTheSky(World world, BlockPos pos) {
      if (!world.provider.hasSkyLight() && world.canBlockSeeSky(pos)) {
         if (!world.isDaytime()
            || (world.getBiome(pos).canRain() || !(world.getBiome(pos).getRainfall() <= 0.0F)) && (world.isRaining() || world.isThundering())) {
            this.state = GenerationState.NIGHT;
         } else {
            this.state = GenerationState.DAY;
         }
      } else {
         this.state = GenerationState.NIGHT;
      }
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
      Spectral(EnumRarity.EPIC, 32758, TileEntitySpectral.settings.nightPower, 9, 6.0E7, 10000.0, 42000, 6.0),
      Singular(EnumRarity.EPIC, 32758, TileEntitySingular.settings.nightPower, 9, 6.0E7, 10000.0, 42000, 6.0);

      public final double maxCharge;
      public final double transferLimit;
      public final double damageAbsorptionRatio;
      public final int dayEU;
      public final int nightEU;
      public final int tier;
      public final int energyPerDamage;
      public final EnumRarity rarity;
      private final String name = this.name().toLowerCase(Locale.ENGLISH);

      private SolarHelmetTypes(
         EnumRarity rarity, int dayEU, int nightEU, int tier, double maxCharge, double transferLimit, int energyPerDamage, double damageAbsorptionRatio
      ) {
         this.rarity = rarity;
         this.dayEU = dayEU;
         this.nightEU = nightEU;
         this.tier = tier;
         this.maxCharge = maxCharge;
         this.transferLimit = transferLimit;
         this.energyPerDamage = energyPerDamage;
         this.damageAbsorptionRatio = damageAbsorptionRatio;

         assert damageAbsorptionRatio > 0.0;
      }

      public String getName() {
         return this.name + "SolarHelmet";
      }

      protected String getLocalisedName() {
         return "solar_helmets." + this.name;
      }
   }
}
