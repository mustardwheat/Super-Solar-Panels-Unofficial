package com.denfop.ssp.items;

import com.google.common.base.CaseFormat;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.BlocksItems;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IBoostingJetpack;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLeggins extends ItemArmorElectric implements IBoostingJetpack {
   protected final String name;

   public ItemLeggins() {
      this("advancedJetpack");
   }

   protected ItemLeggins(String name) {
      this(name, 3000000.0, 30000.0, 3);
   }

   protected ItemLeggins(String name, double maxCharge, double transferLimit, int tier) {
      super((ItemName)null, (String)null, EntityEquipmentSlot.LEGS, maxCharge, transferLimit, tier);
      ((ItemLeggins)BlocksItems.registerItem(this, new ResourceLocation("super_solar_panels", this.name = name))).setTranslationKey(name);
      this.setMaxDamage(27);
      this.setMaxStackSize(1);
      this.setNoRepair();
   }

   @SideOnly(Side.CLIENT)
   public void registerModels(ItemName name) {
      ModelLoader.setCustomModelResourceLocation(
         this, 0, new ModelResourceLocation("super_solar_panels:" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name), (String)null)
      );
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return "super_solar_panels:textures/armour/" + this.name + ".png";
   }

   public String getTranslationKey() {
      return "super_solar_panels." + super.getTranslationKey().substring(4);
   }

   public EnumRarity getRarity(ItemStack stack) {
      return EnumRarity.UNCOMMON;
   }

   public static boolean isJetpackOn(ItemStack stack) {
      return StackUtil.getOrCreateNbtData(stack).getBoolean("isFlyActive");
   }

   public static boolean isHovering(ItemStack stack) {
      return StackUtil.getOrCreateNbtData(stack).getBoolean("hoverMode");
   }

   public static boolean switchJetpack(ItemStack stack) {
      NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
      boolean newMode;
      nbt.setBoolean("isFlyActive", newMode = !nbt.getBoolean("isFlyActive"));
      return newMode;
   }

   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
      NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
      byte toggleTimer = nbt.getByte("toggleTimer");
      if (toggleTimer > 0 && !isJetpackOn(stack)) {
         String s = "toggleTimer";
         nbt.setByte("toggleTimer", --toggleTimer);
      }
   }

   public boolean isJetpackActive(ItemStack stack) {
      return isJetpackOn(stack);
   }

   public double getChargeLevel(ItemStack stack) {
      return ElectricItem.manager.getCharge(stack) / this.getMaxCharge(stack);
   }

   public float getPower(ItemStack stack) {
      return 1.0F;
   }

   public float getDropPercentage(ItemStack stack) {
      return 0.05F;
   }

   public float getBaseThrust(ItemStack stack, boolean hover) {
      return hover ? 0.65F : 0.3F;
   }

   public float getBoostThrust(EntityPlayer player, ItemStack stack, boolean hover) {
      return IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.getCharge(stack) >= 60.0 ? (hover ? 0.07F : 0.09F) : 0.0F;
   }

   public boolean useBoostPower(ItemStack stack, float boostAmount) {
      return ElectricItem.manager.discharge(stack, 60.0, Integer.MAX_VALUE, true, false, false) > 0.0;
   }

   public float getWorldHeightDivisor(ItemStack stack) {
      return 1.0F;
   }

   public float getHoverMultiplier(ItemStack stack, boolean upwards) {
      return 0.2F;
   }

   public float getHoverBoost(EntityPlayer player, ItemStack stack, boolean up) {
      if (IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.getCharge(stack) >= 60.0) {
         if (!player.onGround) {
            ElectricItem.manager.discharge(stack, 60.0, Integer.MAX_VALUE, true, false, false);
         }

         return 2.0F;
      } else {
         return 1.0F;
      }
   }

   public boolean drainEnergy(ItemStack pack, int amount) {
      return ElectricItem.manager.discharge(pack, amount * 6, Integer.MAX_VALUE, true, false, false) > 0.0;
   }

   public boolean canProvideEnergy(ItemStack stack) {
      return true;
   }

   public int getEnergyPerDamage() {
      return 0;
   }

   public double getDamageAbsorptionRatio() {
      return 0.0;
   }
}
