package com.nfcat.papermc.commands.cloud;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Nfcat implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (strings.length == 0) return send(commandSender, menu);
        return switch (strings[0]) {
            default -> send(commandSender, menu);
        };
    }

    private boolean send(CommandSender sender, String s) {
        sender.sendMessage(s);
        return true;
    }

    final String menu = """
             
            /n 获取帮助 (/nf || /nfcat)
            -
            目录:
            第一页：聚合菜单
            第二页：用户操作
            -
            第0页/共2页
            ------------
            """;
}
