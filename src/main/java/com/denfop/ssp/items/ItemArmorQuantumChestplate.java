package com.denfop.ssp.items;

import com.denfop.ssp.SspConfig;
import ic2.api.item.ElectricItem;
import ic2.api.item.IHazmatLike;
import ic2.core.IC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemArmorQuantumChestplate extends ItemChest implements IHazmatLike {
   protected static final int DEFAULT_COLOUR = -1;

   public ItemArmorQuantumChestplate() {
      super("graviChestplate1", SspConfig.quantumChestplateMaxCharge, 100000.0, SspConfig.quantumChestplateTier);
   }

   @Override
   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return "super_solar_panels:textures/armour/" + this.name + (type != null ? "Overlay" : "") + ".png";
   }

   public void setColor(ItemStack stack, int colour) {
      this.getDisplayNbt(stack, true).setInteger("colour", colour);
   }

   public boolean hasColor(ItemStack stack) {
      return this.getColor(stack) != DEFAULT_COLOUR;
   }

   public int getColor(ItemStack stack) {
      NBTTagCompound nbt = this.getDisplayNbt(stack, false);
      return nbt != null && nbt.hasKey("colour", 3) ? nbt.getInteger("colour") : DEFAULT_COLOUR;
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
      if (!nbt.hasKey("display", 10)) {
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

   @Override
   public EnumRarity getRarity(ItemStack stack) {
      return EnumRarity.EPIC;
   }

   // IHazmatLike检查：四件齐全且均有电时，背包内放射物不再施加辐射
   @Override
   public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
      return ElectricItem.manager.getCharge(stack) > 0.0;
   }

   @Override
   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
      super.onArmorTick(world, player, stack);
      if (ElectricItem.manager.getCharge(stack) <= 0.0) {
         return;
      }
      player.extinguish();
      if (SspConfig.effectFireResistance) {
         player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 300, 2, true, true));
      }
      if (SspConfig.effectStrength) {
         player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 0, true, true));
      }
   }

   @Override
   public boolean isJetpackActive(ItemStack stack) {
      return ElectricItem.manager.getCharge(stack) >= 10000.0;
   }

   @Override
   public float getPower(ItemStack stack) {
      return 1.5F;
   }

   @Override
   public float getDropPercentage(ItemStack stack) {
      return 0.01F;
   }

   @Override
   public float getBaseThrust(ItemStack stack, boolean hover) {
      return hover ? 1.0F : 0.5F;
   }

   @Override
   public float getBoostThrust(EntityPlayer player, ItemStack stack, boolean hover) {
      return IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.getCharge(stack) >= 834.0 ? (hover ? 0.1F : 0.3F) : 0.0F;
   }

   @Override
   public boolean useBoostPower(ItemStack stack, float boostAmount) {
      return ElectricItem.manager.discharge(stack, 834.0, Integer.MAX_VALUE, true, false, false) > 0.0;
   }

   @Override
   public float getWorldHeightDivisor(ItemStack stack) {
      return 0.91071427F;
   }

   @Override
   public float getHoverMultiplier(ItemStack stack, boolean upwards) {
      return 0.25F;
   }

   @Override
   public float getHoverBoost(EntityPlayer player, ItemStack stack, boolean up) {
      if (IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.getCharge(stack) >= 834.0) {
         if (!player.onGround) {
            ElectricItem.manager.discharge(stack, 834.0, Integer.MAX_VALUE, true, false, false);
         }

         return 3.0F;
      } else {
         return 1.0F;
      }
   }

   @Override
   public boolean drainEnergy(ItemStack pack, int amount) {
      return ElectricItem.manager.discharge(pack, 278.0, Integer.MAX_VALUE, true, false, false) > 0.0;
   }

   @Override
   public int getEnergyPerDamage() {
      return 20000;
   }

   @Override
   public double getDamageAbsorptionRatio() {
      return 1.5;
   }

   @Override
   public boolean canProvideEnergy(ItemStack stack) {
      return true;
   }
}
