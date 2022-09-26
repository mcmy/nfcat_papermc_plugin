package com.nfcat.papermc.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BiNfcat implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (strings.length == 0) return send(commandSender, "条件错误");
        return switch (strings[0]) {
            default -> send(commandSender, Bukkit.getPlayer(commandSender.getName()).getLocation().toString());
        };
    }

    private boolean send(CommandSender sender, String s) {
        sender.sendMessage(s);
        return true;
    }
}
