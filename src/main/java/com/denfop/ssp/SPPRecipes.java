package com.denfop.ssp;

import com.denfop.ssp.items.CraftingThings;
import com.denfop.ssp.tiles.SSPBlock;
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
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistryModifiable;

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
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.IRRADIANT_GLASS_PANE),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.bluecomponent)
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
      // 强化量子套装（胸甲/护腿/靴子），配方布局与 1.4 及强化量子头盔一致
      addShapedRecipe(
         new ItemStack(SSP_Items.Quantum_chestplate.getInstance()),
         "CBC",
         "CAC",
         " H ",
         'A',
         IC2Items.getItem("quantum_chestplate"),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantumitems6),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems3),
         'H',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems5)
      );
      addShapedRecipe(
         new ItemStack(SSP_Items.Quantum_leggins.getInstance()),
         "CBC",
         "CAC",
         " H ",
         'A',
         IC2Items.getItem("quantum_leggings"),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantumitems6),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems3),
         'H',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems5)
      );
      addShapedRecipe(
         new ItemStack(SSP_Items.Quantum_boosts.getInstance()),
         "CBC",
         "CAC",
         " H ",
         'A',
         IC2Items.getItem("quantum_boots"),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantumitems6),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems3),
         'H',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems5)
      );
      // 强化量子头盔（1.4 移植）
      addShapedRecipe(
         new ItemStack(SSP_Items.QUANTUM_HELMET.getInstance()),
         "CBC",
         "CAC",
         " H ",
         'A',
         IC2Items.getItem("quantum_helmet"),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantumitems6),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems3),
         'H',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems5)
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
      // 以下为 1.4 移植配方：纳米工具箱、高级铱板压缩链、量子工具箱与光谱电路
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.nanobox),
         "ACA",
         "CBC",
         "ACA",
         'A',
         IC2Items.getItem("crafting", "carbon_plate"),
         'B',
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.IRIDIUM_IRON_PLATE),
         'C',
         IC2Items.getItem("crafting", "alloy")
      );
      addCompressorRecipe(
         input.forOreDict("ingotIridium", 9),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems2)
      );
      addCompressorRecipe(
         input.forStack(SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems2), 9),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems4)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems3),
         "ACA",
         "CBC",
         "ACA",
         'A',
         IC2Items.getItem("crafting", "carbon_plate"),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.nanobox),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems4)
      );
      addShapedRecipe(
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems5),
         "ACA",
         "CBC",
         "ACA",
         'A',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems3),
         'B',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.quantumitems6),
         'C',
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.QuantumItems2)
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

   /**
    * 改造 ASP 自有的三个太阳能配方：原位替换指定材料，其余槽位与布局保持不变。
    * 必须在 ASP 完成配方注册之后（本 mod 的 postInit）调用。
    */
   static void replaceAspPanelRecipes() {
      // 高级太阳能：第二格的光辉玻璃板替换为高级核心
      swapRecipeIngredient(
         AdvancedSolarPanels.machines.getItemStack(TEs.advanced_solar_panel),
         ASP_Items.CRAFTING.getItemStack(CraftingTypes.IRRADIANT_GLASS_PANE),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.advanced_core),
         1
      );
      // 混合太阳能：青金石块替换为混合核心
      swapRecipeIngredient(
         AdvancedSolarPanels.machines.getItemStack(TEs.hybrid_solar_panel),
         new ItemStack(Blocks.LAPIS_BLOCK),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.hybrid_core),
         -1
      );
      // 终极混合太阳能：青金石块替换为终极混合核心
      swapRecipeIngredient(
         AdvancedSolarPanels.machines.getItemStack(TEs.ultimate_solar_panel),
         new ItemStack(Blocks.LAPIS_BLOCK),
         SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.ultimate_core),
         -1
      );
   }

   /**
    * 在配方注册表中查找产出为 output 的有序配方，将匹配 target 的槽位材料替换为 replacement。
    * slotIndex >= 0 时仅替换该行优先索引处的槽位，为 -1 时替换所有匹配槽位。
    * 不含目标材料的配方（例如 ASP 简易配方分支）保持原样，不做任何修改。
    */
   private static void swapRecipeIngredient(ItemStack output, ItemStack target, ItemStack replacement, int slotIndex) {
      IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>)ForgeRegistries.RECIPES;
      List<ResourceLocation> removed = new ArrayList<>();
      List<IRecipe> added = new ArrayList<>();
      for (IRecipe recipe : ForgeRegistries.RECIPES) {
         if (!(recipe instanceof IShapedRecipe)) {
            continue;
         }
         ItemStack result = recipe.getRecipeOutput();
         if (result.isEmpty() || !ItemStack.areItemsEqual(result, output)) {
            continue;
         }
         IShapedRecipe shaped = (IShapedRecipe)recipe;
         int width = shaped.getRecipeWidth();
         int height = shaped.getRecipeHeight();
         NonNullList<Ingredient> grid = recipe.getIngredients();
         if (grid.size() < width * height) {
            continue;
         }
         // 标记需要替换的槽位
         boolean[] swap = new boolean[width * height];
         boolean found = false;
         for (int i = 0; i < swap.length; i++) {
            if ((slotIndex < 0 || i == slotIndex) && grid.get(i).apply(target)) {
               swap[i] = true;
               found = true;
            }
         }
         if (!found) {
            continue;
         }
         // 重建配方参数：空槽记为空格，其余槽位按材料实例分配占位字符
         List<Object> args = new ArrayList<>();
         Map<Object, Character> symbols = new IdentityHashMap<>();
         char nextSymbol = 'A';
         for (int y = 0; y < height; y++) {
            StringBuilder row = new StringBuilder(width);
            for (int x = 0; x < width; x++) {
               int index = y * width + x;
               Ingredient ingredient = grid.get(index);
               if (!swap[index] && (ingredient == Ingredient.EMPTY || ingredient.getMatchingStacks().length == 0)) {
                  row.append(' ');
                  continue;
               }
               Object cell = swap[index] ? (Object)replacement : (Object)ingredient;
               Character symbol = symbols.get(cell);
               if (symbol == null) {
                  symbol = nextSymbol++;
                  symbols.put(cell, symbol);
               }
               row.append(symbol.charValue());
            }
            args.add(row.toString());
         }
         for (Map.Entry<Object, Character> entry : symbols.entrySet()) {
            args.add(entry.getValue());
            args.add(entry.getKey());
         }
         ShapedOreRecipe copy = new ShapedOreRecipe(null, result.copy(), args.toArray());
         ResourceLocation origin = recipe.getRegistryName();
         copy.setRegistryName(new ResourceLocation(SuperSolarPanels.MODID, "patched_" + origin.getPath() + "_" + removed.size()));
         removed.add(origin);
         added.add(copy);
      }
      for (ResourceLocation name : removed) {
         registry.remove(name);
      }
      for (IRecipe recipe : added) {
         registry.register(recipe);
      }
      SuperSolarPanels.log.info("Replaced {} ASP recipe(s) for {}", removed.size(), output.getTranslationKey());
   }
}
