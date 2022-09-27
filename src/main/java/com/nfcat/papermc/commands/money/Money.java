package com.nfcat.papermc.commands.money;

import com.nfcat.papermc.sql.SQLManager;
import com.nfcat.papermc.sql.entry.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Money implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        switch (strings[0]) {
            case "chongzhi" -> {
                String s1 = "tellraw" + commandSender.getName() + "{\"text\":\"点我打开 nfcat.com\",\"clickEvent\":{\"action\":\"open_url,\"value\":\"https://nfcat.com/\"}}";
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s1);
            }
            case "chaxun" -> {
                User user = SQLManager.selectInfo(commandSender.getName());
                commandSender.sendMessage("你的金币余额为" + user.getGold());
                commandSender.sendMessage("你的nmb余额为" + user.getNmb());
            }
            case "pay" -> {
                User user = SQLManager.selectInfo(strings[1]);
                User user1 = SQLManager.selectInfo(commandSender.getName());
                if (user == null || user.getMcName().length() == 0) {
                    commandSender.sendMessage("请确认收款方信息是否正确");
                    return false;
                }
            }
            default -> {
                commandSender.sendMessage("参数有误，请重新输入");
            }
        }
        return false;
    }
}
