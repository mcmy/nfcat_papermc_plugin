package com.nfcat.spigotmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Menu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) return send(commandSender, menu);
        return switch (strings[0]) {
            case "1" -> send(commandSender, menu1);
            case "2" -> send(commandSender, menu2);
            default -> send(commandSender, menu);
        };
    }

    private boolean send(CommandSender sender, String s) {
        sender.sendMessage(s);
        return true;
    }

    final String menu = """
            /m 获取帮助 (/menu)
            /m N 第N页 (/menu N)
            目录:
            第一页：用户操作-+-第二页：啥也没有
            第0页/共2页
            """;
    final String menu1 = """
            用户相关
            /l <password> 登录 (/log || /login)
            /r <password> <re-password> 注册 (/reg || /register)
            /c <old-password> <new-password> 修改密码 (/changepass)
            第1页/共2页
            """;
    final String menu2 = """
                        
            """;
}
