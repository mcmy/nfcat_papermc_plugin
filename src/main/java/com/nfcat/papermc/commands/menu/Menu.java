package com.nfcat.papermc.commands.menu;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Menu implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (strings.length == 0) return send(commandSender, menu);
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"summon tnt");
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
            -
            目录:
            第一页：聚合菜单
            第二页：用户操作
            -
            第0页/共2页
            ------------
            """;
    final String menu1 = """
             
            其他二级命令:
            /nf <command>(?)云服务
            /shop <command>(?) 商店
            /bank <command>(?) 银行
            /card <command>(?) 卡密系统
            -
            第1页/共2页
            ------------
            """;
    final String menu2 = """
             
            用户相关:
            /l <password> 登录 (/log || /login)
            /r <password> <re-password> 注册 (/reg || /register)
            /c <old-password> <new-password> 修改密码 (/changepass)
            -
            第2页/共2页
            ------------
            """;
}
