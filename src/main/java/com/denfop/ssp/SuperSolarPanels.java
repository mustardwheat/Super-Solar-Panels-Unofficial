package com.denfop.ssp;

import com.denfop.ssp.items.CraftingThings;
import com.denfop.ssp.tiles.SSPBlock;
import com.chocohead.advsolar.ASP_Items;
import com.chocohead.advsolar.items.ItemCraftingThings;
import com.chocohead.advsolar.tiles.TEs;
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
   dependencies = "required-after:advanced_solar_panels@[4.3.0,);required-after:advsolarpatch@[1.2.2,);",
   version = "1.3.0",
   acceptedMinecraftVersions = "[1.12,1.12.2]"
)
public final class SuperSolarPanels {
   public static final String MODID = "super_solar_panels";
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
      SspConfig.loadConfig(event.getSuggestedConfigurationFile(), event.getSide().isClient());
      machines = TeBlockRegistry.get(SSPBlock.IDENTITY);
      SspItems.buildItems(event.getSide());
      SSPKeys.addFlyKey();
      OreDictionary.registerOre("enderquantumcomponent", SspItems.CRAFTING.getItemStack(CraftingThings.CraftingTypes.enderquantumcomponent));
      OreDictionary.registerOre("solarsplitter", SspItems.CRAFTING.getItemStack(CraftingThings.CraftingTypes.solarsplitter));
      OreDictionary.registerOre("bluecomponent", SspItems.CRAFTING.getItemStack(CraftingThings.CraftingTypes.bluecomponent));
      OreDictionary.registerOre("redcomponent", SspItems.CRAFTING.getItemStack(CraftingThings.CraftingTypes.redcomponent));
      // 绿色光谱组件已移除，其矿物词典名转由 ASP 光辉玻璃板继承，保证跨 mod 配方兼容
      OreDictionary.registerOre("greencomponent", ASP_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.IRRADIANT_GLASS_PANE));
      OreDictionary.registerOre("singularcore", SspItems.CRAFTING.getItemStack(CraftingThings.CraftingTypes.singularcore));
      OreDictionary.registerOre("photoniy", SspItems.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy));
      OreDictionary.registerOre("photoniy_ingot", SspItems.CRAFTING.getItemStack(CraftingThings.CraftingTypes.photoniy_ingot));
      OreDictionary.registerOre("spectralcore", SspItems.CRAFTING.getItemStack(CraftingThings.CraftingTypes.spectralcore));
   }

   private void registerJetpackBlacklist() {
      // 强化量子胸甲自带飞行，禁止再附挂喷气背包组件（与 IC2 拉黑原版量子胸甲一致）；
      // 喷气背包配方只接受胸甲槽位的物品，护腿与靴子天然不可附挂，无需列入黑名单
      JetpackAttachmentRecipe.blacklistedItems.add(SspItems.Quantum_chestplate.getInstance());
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      SSPBlock.buildDummies();
      // IC2 机器方块默认仅对 IC2 标签页与搜索页输出子物品，需通过 CreativeRegisterer 扩展点移入本 mod 标签页；
      // 推迟到 init 执行以保证 ASP 的 TeBlock 已注册（addCreativeRegisterer 要求目标 identity 已存在）
      TeBlockRegistry.addCreativeRegisterer(new SSPCreativeRegisterer(SSPBlock.values()), SSPBlock.IDENTITY);
      TeBlockRegistry.addCreativeRegisterer(new SSPCreativeRegisterer(TEs.values()), TEs.IDENTITY);
      SPPRecipes.addCraftingRecipes();
      registerJetpackBlacklist();
   }

   @SubscribeEvent
   @SideOnly(Side.CLIENT)
   public static void doColourThings(Item event) {
      ItemColors colours = event.getItemColors();
      IItemColor armourColouring = (IItemColor)((Map)ReflectionUtil.getFieldValue(ReflectionUtil.getField(ItemColors.class, Map.class), colours))
         .get(Items.LEATHER_BOOTS.delegate);
      colours.registerItemColorHandler(armourColouring, new net.minecraft.item.Item[]{SspItems.Spectral_SOLAR_HELMET.getInstance()});
      colours.registerItemColorHandler(armourColouring, new net.minecraft.item.Item[]{SspItems.Singular_SOLAR_HELMET.getInstance()});
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      // 改造 ASP 面板配方（需在其 init 阶段完成注册之后执行）
      SPPRecipes.replaceAspPanelRecipes();
      // 将 SSP 与 ASP 注册的全部物品统一归入 SuperSolarPanels 创造标签页
      for (net.minecraft.item.Item item : net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS) {
         ResourceLocation registryName = item.getRegistryName();
         if (registryName != null
            && ("super_solar_panels".equals(registryName.getNamespace()) || "advanced_solar_panels".equals(registryName.getNamespace()))) {
            item.setCreativeTab(SSPTab.TAB);
         }
      }

      // ItemBlock 的创造标签页委托给方块，机器方块必须单独设置
      for (net.minecraft.block.Block block : net.minecraftforge.fml.common.registry.ForgeRegistries.BLOCKS) {
         ResourceLocation registryName = block.getRegistryName();
         if (registryName != null
            && ("super_solar_panels".equals(registryName.getNamespace()) || "advanced_solar_panels".equals(registryName.getNamespace()))) {
            block.setCreativeTab(SSPTab.TAB);
         }
      }
   }

}
