package com.nfcat.spigotmc.commands;

import com.nfcat.spigotmc.enums.MatcherString;
import com.nfcat.spigotmc.server.NfcatLoginListener;
import com.nfcat.spigotmc.sql.Manager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Register implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!MatcherString.matcher(MatcherString.USERNAME,commandSender.getName())){
            Bukkit.broadcastMessage("账号格式错误,请修改账号");
            return true;
        }
        if (!strings[0].equals(strings[1])) {
            Bukkit.broadcastMessage("2次密码不一致");
            return true;
        }
        if (!MatcherString.matcher(MatcherString.PASSWORD,strings[0])){
            Bukkit.broadcastMessage("密码过于简单");
            return true;
        }
        if (Manager.register(commandSender.getName(), strings[0])) {
            NfcatLoginListener.loginSuccess(commandSender.getName());
            return true;
        }
        NfcatLoginListener.loginFail(Bukkit.getPlayer(commandSender.getName()), "登录失败");
        return true;
    }
}
