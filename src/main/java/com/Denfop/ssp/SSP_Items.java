package com.Denfop.ssp;

import com.Denfop.ssp.items.CraftingThings;
import com.Denfop.ssp.items.ItemArmorQuantumBoosts;
import com.Denfop.ssp.items.ItemArmorQuantumChestplate;
import com.Denfop.ssp.items.ItemArmorQuantumLeggins;
import com.Denfop.ssp.items.ItemArmourSolarHelmet;
import ic2.core.block.state.IIdProvider;
import ic2.core.ref.IMultiItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum SSP_Items {
   Spectral_SOLAR_HELMET,
   Singular_SOLAR_HELMET,
   Quantum_chestplate,
   Quantum_leggins,
   Quantum_boosts,
   CRAFTING;

   private Item instance;

   public <T extends Item> T getInstance() {
      return (T)this.instance;
   }

   public <T extends Enum> ItemStack getItemStack(T variant) {
      if (this.instance == null) {
         return null;
      } else if (this.instance instanceof IMultiItem) {
         IMultiItem<IIdProvider> multiItem = (IMultiItem<IIdProvider>)this.instance;
         return multiItem.getItemStack((IIdProvider)variant);
      } else if (variant == null) {
         return new ItemStack(this.instance);
      } else {
         throw new IllegalArgumentException("Not applicable");
      }
   }

   public <T extends Item> void setInstance(T instance) {
      if (this.instance != null) {
         throw new IllegalStateException("Duplicate instances!");
      } else {
         this.instance = instance;
      }
   }

   static void buildItems(Side side) {
      Singular_SOLAR_HELMET.setInstance(new ItemArmourSolarHelmet(ItemArmourSolarHelmet.SolarHelmetTypes.Singular));
      Spectral_SOLAR_HELMET.setInstance(new ItemArmourSolarHelmet(ItemArmourSolarHelmet.SolarHelmetTypes.Spectral));
      CRAFTING.setInstance(new CraftingThings());
      Quantum_chestplate.setInstance(new ItemArmorQuantumChestplate());
      Quantum_leggins.setInstance(new ItemArmorQuantumLeggins());
      Quantum_boosts.setInstance(new ItemArmorQuantumBoosts());
      if (side == Side.CLIENT) {
         doModelGuf();
      }
   }

   @SideOnly(Side.CLIENT)
   private static void doModelGuf() {
      for (SSP_Items item : values()) {
         ((ic2.core.ref.IItemModelProvider)item.getInstance()).registerModels((ic2.core.ref.ItemName)null);
      }
   }
}
