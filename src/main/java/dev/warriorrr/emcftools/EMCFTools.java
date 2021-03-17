package dev.warriorrr.emcftools;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev.warriorrr.emcftools.commands.SuicideCommand;
import dev.warriorrr.emcftools.listeners.BlockListener;
import dev.warriorrr.emcftools.listeners.PlayerListener;
import net.md_5.bungee.api.ChatColor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class EMCFTools extends JavaPlugin implements Listener {

    public static String prefix = ChatColor.GOLD + "[" + Utils.gradient("EMCF", Color.GREEN, new Color(0, 102, 0)) + ChatColor.GOLD + "]";
    public static Map<UUID, Long> suicideCooldowns = new HashMap<>();
    public static List<UUID> recentlySuicided = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);

        getCommand("suicide").setExecutor(new SuicideCommand());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Entry<UUID, Long> entry : suicideCooldowns.entrySet()) {
                    if (System.currentTimeMillis() > entry.getValue())
                        suicideCooldowns.remove(entry.getKey());
                }
            }
        }, 1200, 1200);
    }
}
