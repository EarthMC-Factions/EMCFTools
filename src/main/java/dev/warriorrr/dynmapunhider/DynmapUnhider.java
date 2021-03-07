package dev.warriorrr.dynmapunhider;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

import net.md_5.bungee.api.ChatColor;

import java.awt.Color;

public class DynmapUnhider extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR)
            public void onPlayerJoin(PlayerJoinEvent event) {
                DynmapAPI api = ((DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap"));
                
                if (!api.getPlayerVisbility(event.getPlayer())) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dynmap show " + event.getPlayer().getName());
                    event.getPlayer().sendMessage(ChatColor.GOLD + "[" + gradient("EarthMC Factions", Color.GREEN, new Color(0, 102, 0)) + ChatColor.GOLD + "]" + ChatColor.AQUA + " You are now shown on Dynmap.");
                }
            }
        }, this);
    }

    public static String gradient(String string, Color from, Color to) {
        int l = string.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            sb.append(ChatColor.of(new Color(
                    (from.getRed() + (i * (1.0F / l) * (to.getRed() - from.getRed()))) / 255,
                    (from.getGreen() + (i * (1.0F / l) * (to.getGreen() - from.getGreen()))) / 255,
                    (from.getBlue() + (i * (1.0F / l) * (to.getBlue() - from.getBlue()))) / 255
            )));
            sb.append(string.charAt(i));
        }
        return sb.toString();
    }
}
