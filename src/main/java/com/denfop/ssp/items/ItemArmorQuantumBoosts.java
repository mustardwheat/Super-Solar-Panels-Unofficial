package com.denfop.ssp.items;

import com.denfop.ssp.SspConfig;
import ic2.api.item.ElectricItem;
import ic2.api.item.IHazmatLike;
import ic2.core.IC2;
import ic2.core.util.StackUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemArmorQuantumBoosts extends ItemBoosts implements IHazmatLike {
   protected static final int DEFAULT_COLOUR = -1;
   private float jumpCharge;

   public ItemArmorQuantumBoosts() {
      super("graviBoosts", SspConfig.quantumBoostsMaxCharge, 100000.0, 4);
      // 与原版量子靴一致：仅靴子实例注册摔落事件总线监听
      MinecraftForge.EVENT_BUS.register(this);
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

   // 辐射防护接入 IC2 的 IHazmatLike 检查：四件齐全且均有电时，背包内放射物不再施加辐射
   @Override
   public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
      return ElectricItem.manager.getCharge(stack) > 0.0;
   }

   @Override
   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
      super.onArmorTick(world, player, stack);
      player.extinguish();
      NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
      if (IC2.platform.isSimulating()) {
         boolean wasOnGround = !nbtData.hasKey("wasOnGround") || nbtData.getBoolean("wasOnGround");
         if (wasOnGround && !player.onGround && IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
            ElectricItem.manager.use(stack, 4000.0, null);
         }

         if (player.onGround != wasOnGround) {
            nbtData.setBoolean("wasOnGround", player.onGround);
         }
      } else {
         if (ElectricItem.manager.canUse(stack, 4000.0) && player.onGround) {
            this.jumpCharge = 1.0F;
         }

         if (player.motionY >= 0.0 && this.jumpCharge > 0.0F && !player.isInWater()) {
            player.motionY = player.motionY + this.jumpCharge * 0.3F;
            this.jumpCharge = (float)(this.jumpCharge * 0.75);
         } else if (this.jumpCharge < 1.0F) {
            this.jumpCharge = 0.0F;
         }
      }

      // 无电时药水效果不生效
      if (SspConfig.canCraftDoubleSlabs && ElectricItem.manager.getCharge(stack) > 0.0) {
         player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300, 0, true, true));
         // 跳跃提升由独立开关 "Disable Effect JUMP BOOST" 控制且默认禁用
         if (SspConfig.canCraftASP) {
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 300, 0, true, true));
         }
         if (SspConfig.canCraftHSP) {
            if (SspConfig.canCraftDoubleSlabs) {
               player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 4, true, true));
            }
         }
      }
   }

   @Override
   public boolean isJetpackActive(ItemStack stack) {
      return super.isJetpackActive(stack) && ElectricItem.manager.getCharge(stack) >= 10000.0;
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

   // 摔落伤害特判：全额吸收，与原版量子靴一致（优先级 10）
   @Override
   public ArmorProperties getProperties(EntityLivingBase player, ItemStack armour, DamageSource source, double damage, int slot) {
      if (source == DamageSource.FALL) {
         int limit = (int)Math.min(Integer.MAX_VALUE, 25.0 * ElectricItem.manager.getCharge(armour) / this.getEnergyPerDamage());
         return new ArmorProperties(10, 1.0, limit);
      }
      return super.getProperties(player, armour, source, damage, slot);
   }

   // 摔落保护：10 格以内免伤不耗电，超出部分每点伤害 20000 EU，电量足够时取消整个摔落事件
   @SubscribeEvent(priority = EventPriority.LOW)
   public void onEntityLivingFallEvent(LivingFallEvent event) {
      if (IC2.platform.isSimulating()) {
         EntityLivingBase entity = event.getEntityLiving();
         ItemStack armor = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);
         if (armor != null && armor.getItem() == this) {
            int fallDamage = Math.max((int)event.getDistance() - 10, 0);
            double energyCost = (double)this.getEnergyPerDamage() * fallDamage;
            if (energyCost <= ElectricItem.manager.getCharge(armor)) {
               ElectricItem.manager.discharge(armor, energyCost, Integer.MAX_VALUE, true, false, false);
               event.setCanceled(true);
            }
         }
      }
   }

   @Override
   public double getDamageAbsorptionRatio() {
      // 有效吸收率 = IC2 靴子槽基础值 0.15 × 1.5 = 22.5%
      return 1.5;
   }

   @Override
   public boolean canProvideEnergy(ItemStack stack) {
      return true;
   }
}
