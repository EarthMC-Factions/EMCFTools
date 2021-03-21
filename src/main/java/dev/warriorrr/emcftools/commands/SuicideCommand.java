package dev.warriorrr.emcftools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

import static dev.warriorrr.emcftools.EMCFTools.prefix;
import static dev.warriorrr.emcftools.EMCFTools.suicideCooldowns;
import static dev.warriorrr.emcftools.EMCFTools.recentlySuicided;

public class SuicideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "This command can only be used in-game.");
            return true;
        }
        
        // Player doesn't have permissions
        Player player = (Player) sender;
        if (!player.hasPermission("emcftools.suicide")) {
            player.sendMessage(prefix + ChatColor.RED + " You do not have enough permissions to use this command.");
            return true;
        }

        // Player is on cooldown
        int cooldownTime = 180;
        if (suicideCooldowns.containsKey(player.getUniqueId()) && suicideCooldowns.get(player.getUniqueId()) > System.currentTimeMillis()) {
            long seconds = (suicideCooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000 % cooldownTime;
            player.sendMessage(prefix + ChatColor.RED + " Command is on cooldown! Time remaining: " + seconds + " seconds.");
            return true;
        }

        if (suicideCooldowns.containsKey(player.getUniqueId()))
            suicideCooldowns.replace(player.getUniqueId(), System.currentTimeMillis() + cooldownTime * 1000);
        else
            suicideCooldowns.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime * 1000);
        
        recentlySuicided.add(player.getUniqueId());
        player.setHealth(0);
        return true;
    }
}
