package com.nfcat.papermc.commands.user;

import com.nfcat.papermc.enums.MatcherString;
import com.nfcat.papermc.listener.NfcatLoginListener;
import com.nfcat.papermc.sql.manager.UserSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Register implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (!MatcherString.matcher(MatcherString.USERNAME, commandSender.getName())) {
            commandSender.sendMessage("账号格式错误,请修改账号");
            return true;
        }
        if (strings.length < 2 || !strings[0].equals(strings[1])) {
            commandSender.sendMessage("2次密码不一致");
            return true;
        }
        if (!MatcherString.matcher(MatcherString.PASSWORD, strings[0])) {
            commandSender.sendMessage("密码过于简单");
            return true;
        }
        if (UserSQLManager.register(commandSender.getName(), strings[0])) {
            NfcatLoginListener.loginSuccess(commandSender.getName());
            return true;
        }
        NfcatLoginListener.loginFail(Bukkit.getPlayer(commandSender.getName()), "登录失败");
        return true;
    }
}
