package br.com.devmastersouza.wgextraflags;

import br.com.devmastersouza.wgextraflags.flags.BlockFlag;
import br.com.devmastersouza.wgextraflags.objects.BlockData;
import com.sk89q.worldguard.protection.flags.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/* ESTE PLUGIN PRECISA DE JAVA 1.8 */
public final class WG152ExtraFlags extends JavaPlugin {

    /* Novas flags */
    public static StateFlag EF_FALL_DAMAGE = new StateFlag("EF-fall-damage", true);
    public static StateFlag EF_BLOCK_BREAK = new StateFlag("EF-block-break", true);
    public static StateFlag EF_BLOCK_PLACE = new StateFlag("EF-block-place", true);
    public static StateFlag EF_ITEM_PICKUP = new StateFlag("EF-item-pickup", true);
    public static StateFlag EF_CAN_FLY = new StateFlag("EF-can-fly", true);
    public static LocationFlag EF_TELEPORT_ON_ENTRY = new LocationFlag("EF-teleport-on-entry", RegionGroup.NON_MEMBERS);
    public static LocationFlag EF_TELEPORT_ON_EXIT = new LocationFlag("EF-teleport-on-exit", RegionGroup.NON_MEMBERS);
    public static StringFlag EF_COMMAND_ON_ENTRY = new StringFlag("EF-command-on-entry");
    public static StringFlag EF_COMMAND_ON_EXIT = new StringFlag("EF-command-on-exit");
    public static SetFlag<BlockData> EF_BLOCKED_BLOCKS_BREAK = new SetFlag<BlockData>("EF-blocked-blocks-break", new BlockFlag(null));
    public static SetFlag<BlockData> EF_ALLOWED_BLOCKS_BREAK = new SetFlag<BlockData>("EF-allowed-blocks-break", new BlockFlag(null));
    public static SetFlag<BlockData> EF_BLOCKED_BLOCKS_PLACE = new SetFlag<BlockData>("EF-blocked-blocks-place", new BlockFlag(null));
    public static SetFlag<BlockData> EF_ALLOWED_BLOCKS_PLACE = new SetFlag<BlockData>("EF-allowed-blocks-place", new BlockFlag(null));

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
            addFlag(EF_TELEPORT_ON_ENTRY);
            addFlag(EF_TELEPORT_ON_EXIT);
            addFlag(EF_COMMAND_ON_ENTRY);
            addFlag(EF_COMMAND_ON_EXIT);
            addFlag(EF_BLOCKED_BLOCKS_BREAK);
            addFlag(EF_BLOCKED_BLOCKS_PLACE);
            addFlag(EF_ALLOWED_BLOCKS_BREAK);
            addFlag(EF_ALLOWED_BLOCKS_PLACE);
        }else{
            /* Desativar o plugin se nao tiver o WorldGuard */
            getServer().getPluginManager().disablePlugin(this);
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "WG152ExtraFlags - WORLDGUARD FALTANDO");
        }
    }

    @Override
    public void onEnable() {
        /* Registrando os eventos das flags */
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
    public <T extends Flag<V>, V> V getFlag(T flag, Location location) {return utils.getFlag(flag,location);}
    public <T extends Flag<V>, V> V getFlag(T flag, Location location, Player player) {return utils.getFlag(flag,location,player);}
    /*public ProtectedRegion getRegion(String name, World world) {return utils.getRegion(name,world);}
    public void saveWorldGuard(World world) {utils.saveWorldGuard(world);}*/
}

