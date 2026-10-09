package com.denfop.ssp;

import ic2.core.block.BlockTileEntity;
import ic2.core.block.ITeBlock;
import ic2.core.item.block.ItemBlockTileEntity;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * IC2 机器方块的创造模式子物品注册器。
 * IC2 默认实现仅在 IC2 标签页与搜索页输出子物品（见 BlockTileEntity#getSubBlocks），
 * 通过该扩展点将 SSP 与 ASP 的机器移入本 mod 标签页，同时保留搜索页可见。
 */
public final class SSPCreativeRegisterer implements ITeBlock.ITeBlockCreativeRegisterer {
   private final ITeBlock[] teBlocks;

   public SSPCreativeRegisterer(ITeBlock[] teBlocks) {
      this.teBlocks = teBlocks;
   }

   @Override
   public void addSubBlocks(NonNullList<ItemStack> list, BlockTileEntity block, ItemBlockTileEntity item, CreativeTabs tab) {
      if (tab == SSPTab.TAB || tab == CreativeTabs.SEARCH) {
         for (ITeBlock teBlock : this.teBlocks) {
            if (teBlock.hasItem()) {
               list.add(block.getItemStack(teBlock));
            }
         }
      }
   }
}
