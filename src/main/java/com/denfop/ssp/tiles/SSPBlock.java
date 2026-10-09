package com.denfop.ssp.tiles;

import ic2.core.block.ITeBlock;
import ic2.core.block.TileEntityBlock;
import ic2.core.ref.TeBlock.DefaultDrop;
import ic2.core.ref.TeBlock.HarvestTool;
import ic2.core.util.Util;
import java.util.Set;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum SSPBlock implements ITeBlock {
   spectral_solar_panel(TileEntitySpectral.class, 2),
   singular_solar_panel(TileEntitySingular.class, 3, EnumRarity.RARE),
   admin_solar_panel(TileEntityAdmin.class, 4, EnumRarity.EPIC),
   photonic_solar_panel(TileEntityPhotonic.class, 5, EnumRarity.EPIC);

   private final Class<? extends TileEntityBlock> teClass;
   private final int itemMeta;
   private final EnumRarity rarity;
   private TileEntityBlock dummyTe;
   private static final SSPBlock[] VALUES = values();
   public static final ResourceLocation IDENTITY = new ResourceLocation("super_solar_panels", "machines");

   private SSPBlock(Class<? extends TileEntityBlock> teClass, int itemMeta) {
      this(teClass, itemMeta, EnumRarity.UNCOMMON);
   }

   private SSPBlock(Class<? extends TileEntityBlock> teClass, int itemMeta, EnumRarity rarity) {
      this.teClass = teClass;
      this.itemMeta = itemMeta;
      this.rarity = rarity;
      GameRegistry.registerTileEntity(teClass, "suoer_solar_panels:" + this.getName());
   }

   public boolean hasItem() {
      return true;
   }

   public String getName() {
      return this.name();
   }

   public int getId() {
      return this.itemMeta;
   }

   public ResourceLocation getIdentifier() {
      return IDENTITY;
   }

   public Class<? extends TileEntityBlock> getTeClass() {
      return this.teClass;
   }

   public float getHardness() {
      return 3.0F;
   }

   public float getExplosionResistance() {
      return 15.0F;
   }

   public HarvestTool getHarvestTool() {
      return HarvestTool.Pickaxe;
   }

   public DefaultDrop getDefaultDrop() {
      return DefaultDrop.Self;
   }

   public boolean allowWrenchRotating() {
      return false;
   }

   public Set<EnumFacing> getSupportedFacings() {
      return Util.horizontalFacings;
   }

   public EnumRarity getRarity() {
      return this.rarity;
   }

   public static void buildDummies() {
      ModContainer mc = Loader.instance().activeModContainer();
      if (mc != null && "super_solar_panels".equals(mc.getModId())) {
         for (SSPBlock block : VALUES) {
            if (block.teClass != null) {
               try {
                  block.dummyTe = block.teClass.getDeclaredConstructor().newInstance();
               } catch (Exception var6) {
                  if (Util.inDev()) {
                     var6.printStackTrace();
                  }
               }
            }
         }
      } else {
         throw new IllegalAccessError("Don't mess with this please.");
      }
   }

   public TileEntityBlock getDummyTe() {
      return this.dummyTe;
   }

   public boolean hasActive() {
      return false;
   }
}
