package br.com.devmastersouza.wgextraflags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/* ESTE PLUGIN PRECISA DE JAVA 1.8 */
public class Listeners implements Listener{

    private final WG152ExtraFlags plugin;

    public Listeners(WG152ExtraFlags plugin) {
        this.plugin = plugin;
    }

    /* EF_FALL_DAMAGE */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void fallDamageFlag(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if(player.hasPermission("WG152ExtraFlags.bypass.fall-damage")) return;

                if(!plugin.allows(WG152ExtraFlags.EF_FALL_DAMAGE, player.getLocation())) {
                    String msg = plugin.getConfig().getString("msgs.flags.fall-damage");
                    if(msg != null && !msg.equalsIgnoreCase("null"))
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    event.setCancelled(true);
                }
            }
        }
    }

    /* EF_BLOCK_BREAK */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void blockBreakFlag(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("WG152ExtraFlags.bypass.block-break")) return;
        if(!plugin.allows(WG152ExtraFlags.EF_BLOCK_BREAK, event.getBlock().getLocation())) {
            String msg = plugin.getConfig().getString("msgs.flags.block-break");
            if(msg != null && !msg.equalsIgnoreCase("null"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            event.setCancelled(true);
        }
    }

    /* EF_BLOCK_PLACE */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void blockPlaceFlag(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("WG152ExtraFlags.bypass.block-place")) return;
        if(!plugin.allows(WG152ExtraFlags.EF_BLOCK_PLACE, event.getBlock().getLocation())) {
            String msg = plugin.getConfig().getString("msgs.flags.block-place");
            if(msg != null && !msg.equalsIgnoreCase("null"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            event.setCancelled(true);
        }
    }

    /* EF_ITEM_PICKUP */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void itemPickupFlag(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("WG152ExtraFlags.bypass.item-pickup")) return;
        if(!plugin.allows(WG152ExtraFlags.EF_ITEM_PICKUP, event.getItem().getLocation())) {
            String msg = plugin.getConfig().getString("msgs.flags.item-pickup");
            if(msg != null && !msg.equalsIgnoreCase("null"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void playerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.getAllowFlight()) {
            if(!player.hasPermission("WG152ExtraFlags.bypass.can-fly")) {
                if (!plugin.allows(WG152ExtraFlags.EF_CAN_FLY, player.getLocation())) {
                    String msg = plugin.getConfig().getString("msgs.flags.can-fly");
                    if (msg != null && !msg.equalsIgnoreCase("null"))
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    player.setFlying(false);
                    player.setAllowFlight(false);
                }
            }
        }

        if (event.getFrom().getBlockX() != event.getTo().getBlockX()
                || event.getFrom().getBlockY() != event.getTo().getBlockY()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            String from_cmd_entry = plugin.getFlag(WG152ExtraFlags.EF_COMMAND_ON_ENTRY, event.getFrom());
            String to_cmd_entry = plugin.getFlag(WG152ExtraFlags.EF_COMMAND_ON_ENTRY, event.getTo());
            if(from_cmd_entry != to_cmd_entry) {
                if(to_cmd_entry != null) {
                    Bukkit.getServer().dispatchCommand(player, to_cmd_entry);
                }
            }
            String from_cmd_exit = plugin.getFlag(WG152ExtraFlags.EF_COMMAND_ON_EXIT, event.getFrom());
            String to_cmd_exit = plugin.getFlag(WG152ExtraFlags.EF_COMMAND_ON_EXIT, event.getTo());
            if(from_cmd_exit != to_cmd_exit) {
                if(from_cmd_exit != null) {
                    Bukkit.getServer().dispatchCommand(player, from_cmd_exit);
                }
            }
        }
    }
}
