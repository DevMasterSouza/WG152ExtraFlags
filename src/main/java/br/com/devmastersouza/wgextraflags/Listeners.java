package br.com.devmastersouza.wgextraflags;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
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
                ApplicableRegionSet set =
                        WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
                if(!set.allows(WG152ExtraFlags.EF_FALL_DAMAGE)) {
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
        ApplicableRegionSet set =
                WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(
                        new Vector(event.getBlock().getX(),event.getBlock().getY(),event.getBlock().getZ())
                );
        if(!set.allows(WG152ExtraFlags.EF_BLOCK_BREAK)) {
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
        ApplicableRegionSet set =
                WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(
                        new Vector(event.getBlock().getX(),event.getBlock().getY(),event.getBlock().getZ())
                );
        if(!set.allows(WG152ExtraFlags.EF_BLOCK_PLACE)) {
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
        ApplicableRegionSet set =
                WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
        if(!set.allows(WG152ExtraFlags.EF_ITEM_PICKUP)) {
            String msg = plugin.getConfig().getString("msgs.flags.item-pickup");
            if(msg != null && !msg.equalsIgnoreCase("null"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void playerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ApplicableRegionSet set =
                WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
        if(player.getAllowFlight()) {
            if(player.hasPermission("WG152ExtraFlags.bypass.can-fly")) return;
            if(!set.allows(WG152ExtraFlags.EF_CAN_FLY)) {
                String msg = plugin.getConfig().getString("msgs.flags.can-fly");
                if(msg != null && !msg.equalsIgnoreCase("null"))
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                player.setFlying(false);
                player.setAllowFlight(false);
            }
        }

        /*if (event.getFrom().getBlockX() != event.getTo().getBlockX()
                || event.getFrom().getBlockY() != event.getTo().getBlockY()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            ApplicableRegionSet to =
                    WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(
                            new Vector(event.getTo().getBlockX(),event.getTo().getBlockY(),event.getTo().getBlockZ()));

        }*/
    }
}
