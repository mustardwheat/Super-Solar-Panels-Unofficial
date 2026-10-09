package com.denfop.ssp;

import ic2.core.block.state.IIdProvider;
import ic2.core.ref.IMultiItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum ItemName {
   lapotron_crystal(ItemFolder.battery, ItemName.PathStyle.FolderAndNameWithSuffix);

   private final ItemFolder folder;
   private final ItemName.PathStyle pathStyle;
   private Item instance;
   public static final ItemName[] values = values();

   private ItemName(ItemFolder folder, ItemName.PathStyle pathStyle) {
      if (folder == null) {
         throw new NullPointerException("null folder");
      } else {
         this.folder = folder;
         this.pathStyle = pathStyle;
      }
   }

   public String getPath(String extraName) {
      StringBuilder ret = new StringBuilder();
      if (this.folder.path != null) {
         ret.append(this.folder.path);
         ret.append('/');
      }

      if (this.pathStyle == ItemName.PathStyle.FolderAndNameWithPrefix && extraName != null) {
         ret.append(extraName);
         ret.append('_');
      }

      if (this.pathStyle != ItemName.PathStyle.FolderAndSubName) {
         String name = this.getName();
         if (this.pathStyle == ItemName.PathStyle.FolderAndNameM2WithSuffix) {
            int pos = name.lastIndexOf(95, name.lastIndexOf(95) - 1);
            ret.append(name.substring(0, pos));
         } else {
            ret.append(name);
         }
      }

      if (this.pathStyle != ItemName.PathStyle.FolderAndNameWithPrefix && extraName != null) {
         if (this.pathStyle != ItemName.PathStyle.FolderAndSubName) {
            if (this.pathStyle != ItemName.PathStyle.FolderAndNameWithSuffix && this.pathStyle != ItemName.PathStyle.FolderAndNameM2WithSuffix) {
               ret.append('/');
            } else {
               ret.append('_');
            }
         }

         ret.append(extraName);
      }

      if (ret.length() == 0) {
         throw new IllegalArgumentException("empty name for " + this + " (" + this.pathStyle + ") with extraName=" + extraName);
      } else {
         return ret.toString();
      }
   }

   private String getName() {
      return this.name();
   }

   public boolean hasInstance() {
      return this.instance != null;
   }

   public <T extends Item & IItemModelProvider> T getInstance() {
      if (this.instance == null) {
         throw new IllegalStateException("the requested item instance for " + this.name() + " isn't set (yet)");
      } else {
         return (T)this.instance;
      }
   }

   public <T extends Item & IItemModelProvider> void setInstance(T instance) {
      if (this.instance != null) {
         throw new IllegalStateException("conflicting instance");
      } else {
         this.instance = instance;
      }
   }

   public ItemStack getItemStack() {
      return this.getItemStack((String)null);
   }

   public <T extends Enum<T> & IIdProvider> ItemStack getItemStack(T variant) {
      if (this.instance == null) {
         return null;
      } else if (this.instance instanceof IMultiItem) {
         IMultiItem<T> multiItem = (IMultiItem<T>)this.instance;
         return multiItem.getItemStack(variant);
      } else if (variant == null) {
         return new ItemStack(this.instance);
      } else {
         throw new IllegalArgumentException("not applicable");
      }
   }

   public <T extends Enum<T> & IIdProvider> ItemStack getItemStack(String variant) {
      if (this.instance == null) {
         return null;
      } else if (this.instance instanceof IMultiItem) {
         IMultiItem<T> multiItem = (IMultiItem<T>)this.instance;
         return multiItem.getItemStack(variant);
      } else if (variant == null) {
         return new ItemStack(this.instance);
      } else {
         throw new IllegalArgumentException("not applicable");
      }
   }

   public String getVariant(ItemStack stack) {
      if (this.instance == null) {
         return null;
      } else {
         return this.instance instanceof IMultiItem ? ((IMultiItem)this.instance).getVariant(stack) : null;
      }
   }

   private static enum PathStyle {
      FolderAndNameAndSubName,
      FolderAndSubName,
      FolderAndNameWithPrefix,
      FolderAndNameWithSuffix,
      FolderAndNameM2WithSuffix;
   }
}
