package com.nfcat.papermc.commands.bank;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Bank implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        switch (strings[0]){
            case "create"->{
                //TODO 创建银行卡
            }
            case "del"->{
                //TODO 注销银行卡
            }
            case "changepwd"->{
                //TODO 修改银行卡密码
            }
            case "changetpye"->{
                //TODO 修改卡类型
            }
            case "save"->{
                //TODO 存钱
            }
            case "take"->{
                //TODO 取钱
            }
            case "select"->{
                //TODO 查询余额
            }
            case"pay"->{
                //TODO 转账
            }
            default -> {

            }
        }
        return true;
    }
    final String menu= """
            /bank 获取帮助
            /bank select 查询余额
            /bank save <num>存钱
            /bank take <num>取钱
            /bank create <type> <pwd> <pwd>创建银行卡
            /bank changepwd <old pwd> <new pwd> 修改密码
            /bank changetype <old type> <new type>修改卡类型
            /bank pay <cardnum> <num> <pwd>转账
            
            -
            第1页/共1页
            ------------
            """;
}
