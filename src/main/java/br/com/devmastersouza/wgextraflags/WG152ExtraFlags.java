package br.com.devmastersouza.wgextraflags;

import com.sk89q.worldguard.protection.flags.LocationFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import org.bukkit.plugin.java.JavaPlugin;

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
    public void onEnable() {
        // Plugin startup logic

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
        //

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
