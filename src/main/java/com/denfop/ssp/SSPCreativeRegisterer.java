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
 * 通过该扩展点将机器输出到指定的目标标签页（SSP 与 ASP 各一页），同时保留搜索页可见。
 */
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
