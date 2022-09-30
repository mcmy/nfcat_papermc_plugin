package com.nfcat.papermc.commands.bank;

import com.nfcat.papermc.configs.MybatisConfig;
import com.nfcat.papermc.sql.manager.UserSQLManager;
import com.nfcat.papermc.sql.entry.NfMcUser;
import com.nfcat.papermc.sql.mapper.NfMcUserMapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.net.http.WebSocket;

public class Money implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        switch (strings[0]){
            case "select"->{
                NfMcUser nfMcUser = UserSQLManager.selectUser(commandSender.getName());
                commandSender.sendMessage("你的金币余额为："+nfMcUser.getGold());
                commandSender.sendMessage("你的水晶余额为："+nfMcUser.getCrystal());
                return true;
            }
            case "add"->{
                commandSender.sendMessage("请单击此链接进行充值");
                commandSender.sendMessage("http://baidu.com/");
                return true;
            }
            case "pay"->{
                if (strings[3].equals("金币")){
                    NfMcUser nfMcUser = UserSQLManager.selectUser(commandSender.getName());
                    if (nfMcUser.getGold()>=Long.parseLong(strings[2])) {
                        if (UserSQLManager.selectUser(strings[2])==null){
                            commandSender.sendMessage("请确认收款方信息并重试");
                            return true;
                        }
                        NfMcUserMapper mapper = MybatisConfig.getSqlSession().getMapper(NfMcUserMapper.class);
                        int i1 = mapper.addGold(strings[1], -Long.parseLong(strings[2]));
                        int i = mapper.addGold(strings[1], Long.parseLong(strings[2]));
                        if (i==1&&i1==1){
                            commandSender.sendMessage("支付成功");
                            return true;
                        }else {
                            commandSender.sendMessage("发生未知错误，请联系管理员");
                            return true;
                        }
                    }else{
                        commandSender.sendMessage("余额不足");
                        return true;
                    }

                }else if (strings[3].equals("水晶")){
                        NfMcUser nfMcUser = UserSQLManager.selectUser(commandSender.getName());
                        if (nfMcUser.getCrystal()>=Long.parseLong(strings[2])) {
                            if (UserSQLManager.selectUser(strings[2])==null){
                                commandSender.sendMessage("请确认收款方信息并重试");
                                return true;
                            }
                            NfMcUserMapper mapper = MybatisConfig.getSqlSession().getMapper(NfMcUserMapper.class);
                            int i = mapper.addCrystal(strings[1], Long.parseLong(strings[2]));
                            int i1 = mapper.addCrystal(strings[1], -Long.parseLong(strings[2]));
                            if (i==1&&i1==0){
                                commandSender.sendMessage("支付成功");
                                return true;
                            }else {
                                commandSender.sendMessage("发生未知错误，请联系管理员");
                                return true;
                            }
                        }else{
                            commandSender.sendMessage("余额不足");
                            return true;
                        }

                }else {
                    commandSender.sendMessage("支付货币错误，请重新输入");
                    return true;
                }
            }
            default -> {
                commandSender.sendMessage(menu);
                return true;
            }
        }
    }
    final String menu= """
                 
            /money 获取帮助
            /money select 查询余额
            /money add 充值
            /money pay <mc_name> <num> <type>支付
            -
            第1页/共1页
            ------------
            """;
}
