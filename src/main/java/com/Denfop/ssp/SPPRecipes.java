package com.Denfop.ssp;

import com.Denfop.ssp.items.CraftingThings;
import com.Denfop.ssp.tiles.SSPBlock;
import com.chocohead.advsolar.ASP_Items;
import com.chocohead.advsolar.AdvancedSolarPanels;
import com.chocohead.advsolar.items.ItemCraftingThings.CraftingTypes;
import com.chocohead.advsolar.tiles.TEs;
import ic2.api.item.IC2Items;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.IRecipeInputFactory;
import ic2.api.recipe.Recipes;
import ic2.core.recipe.ColourCarryingRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

final class SPPRecipes {
   static void addCraftingRecipes() {
      IRecipeInputFactory input = Recipes.inputFactory;
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.redcomponent),
         "AAA",
         "BBB",
         "AAA",
         'A',
         IC2Items.getItem("glass", "reinforced"),
         'B',
         Items.REDSTONE
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.bluecomponent),
         "AAA",
         "BBB",
         "AAA",
         'A',
         IC2Items.getItem("glass", "reinforced"),
         'B',
         new ItemStack(Items.DYE, 1, 4)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.solarsplitter),
         "ABC",
         "ABC",
         "ABC",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.redcomponent),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.greencomponent),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.bluecomponent)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.greencomponent),
         "A  ",
         'A',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.IRRADIANT_GLASS_PANE)
      );
      addShapelessRecipe(
         new ItemStack(SSP_Items.Spectral_SOLAR_HELMET.getInstance()),
         ASP_Items.ULTIMATE_HYBRID_SOLAR_HELMET.getInstance(),
         SuperSolarPanels.machines.getItemStack(SSPBlock.spectral_solar_panel),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.spectralcore)
      );
      addShapelessRecipe(
         new ItemStack(SSP_Items.Singular_SOLAR_HELMET.getInstance()),
         SSP_Items.Spectral_SOLAR_HELMET.getInstance(),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.singularcore)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.enderquantumcomponent),
         "ABA",
         "BCB",
         "ABA",
         'A',
         IC2Items.getItem("crafting", "iridium"),
         'B',
         Items.ENDER_EYE,
         'C',
         Items.NETHER_STAR
      );
      addShapedRecipe(
         SuperSolarPanels.machines.getItemStack(SSPBlock.spectral_solar_panel),
         "BBB",
         "BAB",
         "BBB",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.solarsplitter),
         'B',
         AdvancedSolarPanels.machines.getItemStack(TEs.quantum_solar_panel)
      );
      addShapedRecipe(
         SuperSolarPanels.machines.getItemStack(SSPBlock.singular_solar_panel),
         "BBB",
         "BAB",
         "BBB",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.singularcore),
         'B',
         SuperSolarPanels.machines.getItemStack(SSPBlock.spectral_solar_panel)
      );
      addShapedRecipe(
         SuperSolarPanels.machines.getItemStack(SSPBlock.admin_solar_panel),
         "BBB",
         "BAB",
         "BBB",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantcore2),
         'B',
         SuperSolarPanels.machines.getItemStack(SSPBlock.singular_solar_panel)
      );
      addShapedRecipe(
         SuperSolarPanels.machines.getItemStack(SSPBlock.photonic_solar_panel),
         "BBB",
         "BAB",
         "BBB",
         'B',
         SuperSolarPanels.machines.getItemStack(SSPBlock.admin_solar_panel),
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantcore1)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.singularcore),
         "ABA",
         "DCD",
         "ABA",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.enderquantumcomponent),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy_ingot),
         'B',
         IC2Items.getItem("crafting", "iridium"),
         'D',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.ENRICHED_SUNNARIUM_ALLOY)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy),
         " B ",
         "BAB",
         " B ",
         'A',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.QUANTUM_CORE),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.enderquantumcomponent)
      );
      addCompressorRecipe(
         input.forStack(SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy), 9),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy_ingot)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.spectralcore),
         " B ",
         "BAB",
         " B ",
         'A',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.QUANTUM_CORE),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy)
      );
      addShapedRecipe(
         new ItemStack(SSP_Items.Quantum_chestplate.getInstance()),
         " B ",
         "BAB",
         " B ",
         'A',
         IC2Items.getItem("quantum_chestplate"),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy)
      );
      addShapedRecipe(
         new ItemStack(SSP_Items.Quantum_leggins.getInstance()),
         " B ",
         "BAB",
         " B ",
         'A',
         IC2Items.getItem("quantum_leggings"),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy)
      );
      addShapedRecipe(
         new ItemStack(SSP_Items.Quantum_boosts.getInstance()),
         " B ",
         "BAB",
         " B ",
         'A',
         IC2Items.getItem("quantum_boots"),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.EnrichedSunnariumAlloy3),
         "ABA",
         'A',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.ENRICHED_SUNNARIUM_ALLOY),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy_ingot)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.EnrichedSunnariumAlloy2),
         "ABA",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.EnrichedSunnariumAlloy3),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.spectralcore)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantcore2),
         "ABA",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.EnrichedSunnariumAlloy3),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.singularcore)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantcore1),
         "ABA",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.EnrichedSunnariumAlloy2),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantcore2)
      );
   }

   private static void addShapedColourRecipe(ItemStack output, Object... inputs) {
      ColourCarryingRecipe.addAndRegister(output, inputs);
   }

   private static void addCompressorRecipe(IRecipeInput input, ItemStack output) {
      Recipes.compressor.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[]{output});
   }

   public static void addShapedRecipe(ItemStack output, Object... inputs) {
      Recipes.advRecipes.addRecipe(output, inputs);
   }

   public static void addShapelessRecipe(ItemStack output, Object... inputs) {
      Recipes.advRecipes.addShapelessRecipe(output, inputs);
   }
}
