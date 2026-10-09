package com.denfop.ssp;

import ic2.core.IC2;
import ic2.core.util.Keyboard;
import ic2.core.util.ReflectionUtil;
import ic2.core.util.Keyboard.IKeyWatcher;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

public final class SSPKeys extends Keyboard {
   private static final IKeyWatcher FLY_KEY = new SSPKeys.KeyWatcher(SSPKeys.SSPKey.fly);

   private SSPKeys() {
   }

   static void addFlyKey() {
      IC2.keyboard.addKeyWatcher(FLY_KEY);
   }

   public static boolean isFlyKeyDown(EntityPlayer player) {
      return IC2.keyboard.isKeyDown(player, FLY_KEY);
   }

   private static class KeyWatcher implements IKeyWatcher {
      private final SSPKeys.SSPKey key;

      public KeyWatcher(SSPKeys.SSPKey key) {
         this.key = key;
      }

      public Key getRepresentation() {
         return this.key.key;
      }

      @SideOnly(Side.CLIENT)
      public void checkForKey(Set<Key> pressedKeys) {
         if (GameSettings.isKeyDown(this.key.binding)) {
            pressedKeys.add(this.getRepresentation());
         }
      }
   }

   private static enum SSPKey {
      fly(33, "Fly Key");

      private final Key key = this.addKey(this.name());
      @SideOnly(Side.CLIENT)
      private KeyBinding binding;

      private static Field getKeysField() {
         try {
            Field field = ReflectionUtil.getField(Key.class, new String[]{"keys"});
            ReflectionUtil.getField(Field.class, new String[]{"modifiers"}).setInt(field, field.getModifiers() & -17);
            return field;
         } catch (Exception var1) {
            throw new RuntimeException("Error reflecting keys field!", var1);
         }
      }

      private SSPKey(int keyID, String description) {
         if (IC2.platform.isRendering()) {
            ClientRegistry.registerKeyBinding(
               this.binding = new KeyBinding(
                  description, keyID, "SuperSolarPanels".substring(0, 1).toUpperCase(Locale.ENGLISH) + "SuperSolarPanels".substring(1)
               )
            );
         }
      }

      private Key addKey(String name) {
         Key key = (Key)EnumHelper.addEnum(Key.class, name, new Class[0], new Object[0]);
         ReflectionUtil.setValue(null, getKeysField(), ArrayUtils.add(Key.keys, key));
         return key;
      }
   }
}
