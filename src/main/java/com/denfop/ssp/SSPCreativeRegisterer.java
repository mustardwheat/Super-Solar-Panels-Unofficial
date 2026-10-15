package com.denfop.ssp;

import ic2.core.block.BlockTileEntity;
import ic2.core.block.ITeBlock;
import ic2.core.item.block.ItemBlockTileEntity;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public final class SSPCreativeRegisterer implements ITeBlock.ITeBlockCreativeRegisterer {
   private final CreativeTabs target;
   private final ITeBlock[] teBlocks;

   public SSPCreativeRegisterer(CreativeTabs target, ITeBlock[] teBlocks) {
      this.target = target;
      this.teBlocks = teBlocks;
   }

   @Override
   public void addSubBlocks(NonNullList<ItemStack> list, BlockTileEntity block, ItemBlockTileEntity item, CreativeTabs tab) {
      if (tab == this.target || tab == CreativeTabs.SEARCH) {
         for (ITeBlock teBlock : this.teBlocks) {
            if (teBlock.hasItem()) {
               list.add(block.getItemStack(teBlock));
            }
         }
      }
   }
}
