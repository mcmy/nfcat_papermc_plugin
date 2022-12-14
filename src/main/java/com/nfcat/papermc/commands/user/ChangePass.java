package com.nfcat.papermc.commands.user;

import com.nfcat.papermc.enums.MatcherString;
import com.nfcat.papermc.sql.manager.UserSQLManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ChangePass implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (!MatcherString.matcher(MatcherString.PASSWORD, strings[0])) {
            commandSender.sendMessage("密码格式错误");
            return true;
        }
        if (UserSQLManager.changepass(commandSender.getName(), strings[0], strings[1])) {
            commandSender.sendMessage("密码修改成功");
            return true;
        }
        commandSender.sendMessage("密码修改失败");
        return true;
    }
}
