package com.nfcat.papermc.commands.intimacy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Intimacy implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        switch (strings[0]){
            case"select"->{
                //TODO 查询亲密关系
            }
            case "add"->{
                //TODO 添加亲密关系
            }
            case "del"->{
                //TODO 删除亲密关系

            }
            case "marry"->{
                //TODO 结婚

            }
            default -> {
                commandSender.sendMessage(menu);
            }

        }
        return false;
    }
    final String menu= """
            
            -
            第1页/共1页
            ------------
            
            """;
}
