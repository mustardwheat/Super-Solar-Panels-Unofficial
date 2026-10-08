package com.Denfop.ssp;

import com.Denfop.ssp.items.CraftingThings;
import com.Denfop.ssp.tiles.SSPBlock;
import ic2.api.event.TeBlockFinalCallEvent;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.TeBlockRegistry;
import ic2.core.item.armor.jetpack.JetpackAttachmentRecipe;
import ic2.core.util.ReflectionUtil;
import java.util.Map;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber
@Mod(
   modid = "super_solar_panels",
   name = "Super Solar Panels",
   dependencies = "required-after:advanced_solar_panels@[4.3.0,);",
   version = "1.2.0",
   acceptedMinecraftVersions = "[1.12,1.12.2]"
)
public final class SuperSolarPanels {
   public static boolean seasonal = false;
   public static final String MODID = "super_solar_panels";
   private static final String Urane = null;
   public static Logger log;
   public static BlockTileEntity machines;

   @SubscribeEvent
   public static void register(TeBlockFinalCallEvent event) {
      TeBlockRegistry.addAll(SSPBlock.class, SSPBlock.IDENTITY);
      TeBlockRegistry.setDefaultMaterial(SSPBlock.IDENTITY, Material.ROCK);
   }

   @EventHandler
   public void load(FMLPreInitializationEvent event) {
      log = event.getModLog();
      Configs1.loadConfig(event.getSuggestedConfigurationFile(), event.getSide().isClient());
      machines = TeBlockRegistry.get(SSPBlock.IDENTITY);
      SSP_Items.buildItems(event.getSide());
      SSPKeys.addFlyKey();
      OreDictionary.registerOre("enderquantumcomponent", SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.enderquantumcomponent));
      OreDictionary.registerOre("solarsplitter", SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.solarsplitter));
      OreDictionary.registerOre("bluecomponent", SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.bluecomponent));
      OreDictionary.registerOre("redcomponent", SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.redcomponent));
      OreDictionary.registerOre("singularcore", SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.singularcore));
      OreDictionary.registerOre("photoniy", SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy));
      OreDictionary.registerOre("photoniy_ingot", SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy_ingot));
      OreDictionary.registerOre("spectralcore", SSP_Items.CRAFTING.getItemStack(CraftingThings.CraftingTypes.spectralcore));
   }

   private void registerJetpackBlacklist() {
      JetpackAttachmentRecipe.blacklistedItems.add(SSP_Items.Quantum_chestplate.getInstance());
      JetpackAttachmentRecipe.blacklistedItems.add(SSP_Items.Quantum_leggins.getInstance());
      JetpackAttachmentRecipe.blacklistedItems.add(SSP_Items.Quantum_boosts.getInstance());
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      SSPBlock.buildDummies();
      SPPRecipes.addCraftingRecipes();
   }

   @SubscribeEvent
   @SideOnly(Side.CLIENT)
   public static void doColourThings(Item event) {
      ItemColors colours = event.getItemColors();
      IItemColor armourColouring = (IItemColor)((Map)ReflectionUtil.getFieldValue(ReflectionUtil.getField(ItemColors.class, Map.class), colours))
         .get(Items.LEATHER_BOOTS.delegate);
      colours.registerItemColorHandler(armourColouring, new net.minecraft.item.Item[]{SSP_Items.Spectral_SOLAR_HELMET.getInstance()});
      colours.registerItemColorHandler(armourColouring, new net.minecraft.item.Item[]{SSP_Items.Singular_SOLAR_HELMET.getInstance()});
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      // 将 SSP 与 ASP 注册的全部物品统一归入 SuperSolarPanels 创造标签页
      for (net.minecraft.item.Item item : net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS) {
         ResourceLocation registryName = item.getRegistryName();
         if (registryName != null
            && ("super_solar_panels".equals(registryName.getNamespace()) || "advanced_solar_panels".equals(registryName.getNamespace()))) {
            item.setCreativeTab(SSPTab.TAB);
         }
      }
   }

   public static ResourceLocation getIdentifier(String name) {
      return new ResourceLocation("supersolarpanels", name);
   }
}
