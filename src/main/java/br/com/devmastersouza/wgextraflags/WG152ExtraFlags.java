package br.com.devmastersouza.wgextraflags;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/* ESTE PLUGIN PRECISA DE JAVA 1.8 */
public final class WG152ExtraFlags extends JavaPlugin {

    /* Novas Flags */
    public static StateFlag EF_FALL_DAMAGE = new StateFlag("EF-fall-damage", true);
    public static StateFlag EF_BLOCK_BREAK = new StateFlag("EF-block-break", true);
    public static StateFlag EF_BLOCK_PLACE = new StateFlag("EF-block-place", true);
    public static StateFlag EF_ITEM_PICKUP = new StateFlag("EF-item-pickup", true);
    public static StateFlag EF_CAN_FLY = new StateFlag("EF-can-fly", true);
    /*public static LocationFlag EF_TELEPORT_ON_ENTRY = new LocationFlag("EF-teleport-on-entry");
    public static LocationFlag EF_TELEPORT_ON_EXIT = new LocationFlag("EF-teleport-on-exit");
    public static StringFlag EF_COMMAND_ON_ENTRY = new StringFlag("EF-command-on-entry");
    public static StringFlag EF_COMMAND_ON_EXIT = new StringFlag("EF-command-on-exit");*/

    private WorldGuardUtils utils;


    @Override
    public void onLoad() {
        utils = new WorldGuardUtils(this);
        /* Verificando se tem WorldGuard */
        if(getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            /* Adicionando as flags ao WorldGuard */
            addFlag(EF_FALL_DAMAGE);
            addFlag(EF_BLOCK_BREAK);
            addFlag(EF_BLOCK_PLACE);
            addFlag(EF_ITEM_PICKUP);
            addFlag(EF_CAN_FLY);
            /*addFlag(EF_TELEPORT_ON_ENTRY);
            addFlag(EF_TELEPORT_ON_EXIT);
            addFlag(EF_COMMAND_ON_ENTRY);
            addFlag(EF_COMMAND_ON_EXIT);*/
        }else{
            /* Desativar o plugin se nao tiver o WorldGuard */
            getServer().getPluginManager().disablePlugin(this);
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "WG152ExtraFlags - WORLDGUARD FALTANDO");
        }
    }

    @Override
    public void onEnable() {
        /* Registrando os eventos das Flags */
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        saveDefaultConfig();

        getCommand("extraflags").setExecutor((sender, command, label, args) -> {
            if(args.length > 0) {
                if(args[0].equalsIgnoreCase("reload")) {
                    if(sender.hasPermission("WG152ExtraFlags.cmd.reload")) {
                        reloadConfig();
                        sender.sendMessage(ChatColor.RED + "Config recarregada.");
                    }else{
                        sender.sendMessage(ChatColor
                                .translateAlternateColorCodes('&', getConfig().getString("msgs.nopermission")));
                    }
                    return true;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&6WG152ExtraFlags\n&6Criado por DevMasterSouza\n&6Comandos:\n&6/ef reload - recarregar a config"));
            return true;
        });
    }

    @Override
    public void onDisable() {}

    private void addFlag(Flag<?> flag) {utils.addFlag(flag);}

    public boolean allows(StateFlag flag, Location location) {return utils.allows(flag, location);}
}
