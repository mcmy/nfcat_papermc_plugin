package com.nfcat.spigotmc.commands;

import com.nfcat.spigotmc.enums.MatcherString;
import com.nfcat.spigotmc.sql.Manager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChangePass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!MatcherString.matcher(MatcherString.PASSWORD, strings[0])) {
            commandSender.sendMessage("密码格式错误");
            return true;
        }
        if (Manager.changepass(commandSender.getName(), strings[0], strings[1])) {
            commandSender.sendMessage("密码修改成功");
            return true;
        }
        commandSender.sendMessage("密码修改失败");
        return true;
    }
}
