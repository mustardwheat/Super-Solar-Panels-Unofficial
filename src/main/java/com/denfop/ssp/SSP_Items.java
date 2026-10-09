package com.denfop.ssp;

import com.denfop.ssp.items.CraftingThings;
import com.denfop.ssp.items.ItemArmorQuantumBoosts;
import com.denfop.ssp.items.ItemArmorQuantumChestplate;
import com.denfop.ssp.items.ItemArmorQuantumHelmet;
import com.denfop.ssp.items.ItemArmorQuantumLeggins;
import com.denfop.ssp.items.ItemArmourSolarHelmet;
import com.denfop.ssp.items.reactors.ItemReactorHeatStorage;
import com.denfop.ssp.items.tools.ItemNanoSaber;
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
   QUANTUM_HELMET,
   QUANTUM_SABER,
   SPECTRAL_SABER,
   TWELVE_HEAT_STORAGE,
   MAX_HEAT_STORAGE,
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
      QUANTUM_HELMET.setInstance(new ItemArmorQuantumHelmet(ItemArmorQuantumHelmet.SolarHelmetTypes.Helmet));
      QUANTUM_SABER.setInstance(
         new ItemNanoSaber(
            "quantumsaber",
            10,
            Configs1.saberQuantumMaxCharge,
            Configs1.saberQuantumTransferLimit,
            Configs1.saberQuantumTier,
            Configs1.saberQuantumActiveDamage,
            Configs1.saberQuantumDamage
         )
      );
      SPECTRAL_SABER.setInstance(
         new ItemNanoSaber(
            "spectralsaber",
            10,
            Configs1.saberSpectralMaxCharge,
            Configs1.saberSpectralTransferLimit,
            Configs1.saberSpectralTier,
            Configs1.saberSpectralActiveDamage,
            Configs1.saberSpectralDamage
         )
      );
      TWELVE_HEAT_STORAGE.setInstance(new ItemReactorHeatStorage("twelve_heat_storage", Configs1.twelveHeatStorage));
      MAX_HEAT_STORAGE.setInstance(new ItemReactorHeatStorage("max_heat_storage", Configs1.maxHeatStorage));
      if (side == Side.CLIENT) {
         registerItemModels();
      }
   }

   @SideOnly(Side.CLIENT)
   private static void registerItemModels() {
      for (SSP_Items item : values()) {
         ((ic2.core.ref.IItemModelProvider)item.getInstance()).registerModels((ic2.core.ref.ItemName)null);
      }
   }
}
