package br.com.devmastersouza.wgextraflags;

import com.sk89q.worldguard.protection.flags.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class WG152ExtraFlags extends JavaPlugin {

    public static StateFlag EF_FALL_DAMAGE = new StateFlag("EF-fall-damage", true);
    public static StateFlag EF_BLOCK_BREAK = new StateFlag("EF-block-break", true);
    public static StateFlag EF_BLOCK_PLACE = new StateFlag("EF-block-place", true);
    public static StateFlag EF_ITEM_PICKUP = new StateFlag("EF-item-pickup", true);
    public static StateFlag EF_CAN_FLY = new StateFlag("EF-can-fly", true);
    public static LocationFlag EF_TELEPORT_ON_ENTRY = new LocationFlag("EF-teleport-on-entry");
    public static LocationFlag EF_TELEPORT_ON_EXIT = new LocationFlag("EF-teleport-on-exit");
    public static StringFlag EF_COMMAND_ON_ENTRY = new StringFlag("EF-command-on-entry");
    public static StringFlag EF_COMMAND_ON_EXIT = new StringFlag("EF-command-on-exit");

    @Override
    public void onLoad() {
        if(getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            addFlag(EF_FALL_DAMAGE);
            addFlag(EF_BLOCK_BREAK);
            addFlag(EF_BLOCK_PLACE);
            addFlag(EF_ITEM_PICKUP);
            addFlag(EF_CAN_FLY);
            addFlag(EF_TELEPORT_ON_ENTRY);
            addFlag(EF_TELEPORT_ON_EXIT);
            addFlag(EF_COMMAND_ON_ENTRY);
            addFlag(EF_COMMAND_ON_EXIT);
        }
    }

    @Override
    public void onEnable() {

        //fall-damge
        //invicible
        //block-break
        //block-place
        //item-pickup
        //can-fly
        //teleport-on-entry
        //teleport-on-exit
        //command-on-entry
        //command-on-exit

    }

    @Override
    public void onDisable() {}

    private void addFlag(Flag<?> flag) {
        try {
            Field f = DefaultFlag.class.getField("flagsList");

            Flag<?>[] flags = new Flag<?>[DefaultFlag.flagsList.length + 1];
            System.arraycopy(DefaultFlag.flagsList, 0, flags, 0, DefaultFlag.flagsList.length);

            flags[DefaultFlag.flagsList.length] = flag;

            if (flag == null) {
                throw new NullPointerException("flag null");
            }

            Field modifier = Field.class.getDeclaredField("modifiers");

            modifier.setAccessible(true);
            modifier.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            f.set(null, flags);

        } catch (Exception ex) {}
    }
}
