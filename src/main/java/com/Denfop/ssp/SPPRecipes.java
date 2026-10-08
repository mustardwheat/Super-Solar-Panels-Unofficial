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
import ic2.core.util.StackUtil;
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
      // 以下为 1.4 移植配方：三核心、量子电路板与碳系压缩材料
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.ultimate_core),
         "IPI",
         'I',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.ENRICHED_SUNNARIUM_ALLOY),
         'P',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.SUNNARIUM_ALLOY)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.advanced_core),
         "IPI",
         'I',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.SUNNARIUM),
         'P',
         input.forOreDict("ingotUranium")
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.hybrid_core),
         "IPI",
         'I',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.SUNNARIUM),
         'P',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.SUNNARIUM_ALLOY)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantumitems6),
         " A ",
         "ABA",
         " A ",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.nanobox),
         'B',
         IC2Items.getItem("crafting", "advanced_circuit")
      );
      addCompressorRecipe(
         input.forStack(IC2Items.getItem("crafting", "carbon_plate"), 9),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.compresscarbon)
      );
      addCompressorRecipe(
         input.forStack(IC2Items.getItem("crafting", "alloy"), 9),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.compresscarbonultra)
      );
      addExtrudingRecipe(
         input.forStack(IC2Items.getItem("crafting", "coal_chunk"), 9),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.coal_chunk)
      );
      // 双剑配方：1.4 原配方的纳米粉尘（dust）按维护决议替换为烈焰粉
      addShapedRecipe(
         new ItemStack(SSP_Items.QUANTUM_SABER.getInstance()),
         "O  ",
         "OD ",
         "OBC",
         'O',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.nanobox),
         'B',
         Items.BLAZE_POWDER,
         'D',
         StackUtil.copyWithWildCard(IC2Items.getItem("nano_saber")),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantumitems6)
      );
      addShapedRecipe(
         new ItemStack(SSP_Items.SPECTRAL_SABER.getInstance()),
         "O  ",
         "OD ",
         "OBC",
         'O',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems3),
         'B',
         Items.BLAZE_POWDER,
         'D',
         new ItemStack(SSP_Items.QUANTUM_SABER.getInstance()),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems5)
      );
      // 120k/240k 冷却单元（1.4 移植）
      addShapedColourRecipe(
         new ItemStack(SSP_Items.TWELVE_HEAT_STORAGE.getInstance()),
         "TCT",
         "TGT",
         "TCT",
         'C',
         IC2Items.getItem("hex_heat_storage"),
         'G',
         IC2Items.getItem("plate", "iron"),
         'T',
         IC2Items.getItem("plate", "tin")
      );
      addShapedColourRecipe(
         new ItemStack(SSP_Items.MAX_HEAT_STORAGE.getInstance()),
         "TCT",
         "TGT",
         "TCT",
         'C',
         new ItemStack(SSP_Items.TWELVE_HEAT_STORAGE.getInstance()),
         'G',
         IC2Items.getItem("plate", "iron"),
         'T',
         IC2Items.getItem("plate", "tin")
      );
   }

   private static void addShapedColourRecipe(ItemStack output, Object... inputs) {
      ColourCarryingRecipe.addAndRegister(output, inputs);
   }

   private static void addCompressorRecipe(IRecipeInput input, ItemStack output) {
      Recipes.compressor.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[]{output});
   }

   private static void addExtrudingRecipe(IRecipeInput input, ItemStack output) {
      Recipes.metalformerExtruding.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[]{output});
   }

   public static void addShapedRecipe(ItemStack output, Object... inputs) {
      Recipes.advRecipes.addRecipe(output, inputs);
   }

   public static void addShapelessRecipe(ItemStack output, Object... inputs) {
      Recipes.advRecipes.addShapelessRecipe(output, inputs);
   }
}
