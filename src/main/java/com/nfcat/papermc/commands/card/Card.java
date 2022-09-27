package com.nfcat.papermc.commands.card;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Card implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (strings.length < 2) return send(commandSender, menu);
        switch (strings[0]) {
            case "use" -> {
                return send(commandSender, "请输入卡密");
            }
            case "info" -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), """
                        tellraw moc {"text":"点我打开 nfcat.com","clickEvent":{"action":"open_url","value":"https://nfcat.com/"}}
                        """);
            }
            default -> send(commandSender, menu);
        }
        return true;
    }

    private boolean send(CommandSender sender, String s) {
        sender.sendMessage(s);
        return true;
    }

    final String menu = """
             
            /card 获取帮助
            /card use <card> 使用卡密
            /card info <card> 查询卡密信息
            -
            第1页/共1页
            ------------
            """;
}
