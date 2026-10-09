package com.denfop.ssp;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemModelProvider {
   @SideOnly(Side.CLIENT)
   void registerModels(ItemName var1);
}
