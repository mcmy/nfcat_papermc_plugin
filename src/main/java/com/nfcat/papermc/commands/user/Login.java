package com.nfcat.papermc.commands.user;

import com.nfcat.papermc.enums.MatcherString;
import com.nfcat.papermc.listener.NfcatLoginListener;
import com.nfcat.papermc.sql.manager.UserSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Login implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (!MatcherString.matcher(MatcherString.USERNAME, commandSender.getName())) {
            commandSender.sendMessage("账号格式错误,请修改账号");
            return true;
        }
        if (!MatcherString.matcher(MatcherString.PASSWORD, strings[0])) {
            commandSender.sendMessage("密码格式错误");
            return true;
        }
        if (UserSQLManager.login(commandSender.getName(), strings[0])) {
            NfcatLoginListener.loginSuccess(commandSender.getName());
            return true;
        }
        NfcatLoginListener.loginFail(Bukkit.getPlayer(commandSender.getName()), "登录失败");
        return true;
    }
}
