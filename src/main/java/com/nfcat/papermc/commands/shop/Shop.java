package com.nfcat.papermc.commands.shop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfcat.papermc.configs.MybatisConfig;
import com.nfcat.papermc.entity.Shopobj;
import com.nfcat.papermc.mapper.ShopMapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Shop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        switch (strings[0]) {
            case "create" -> {

                if (strings[1].length() == 0) {
                    commandSender.sendMessage("请输入店铺名称");
                    return true;
                }

            }
            case "fand" -> {
                //TODO 搜索店铺
            }
            case "myshop" -> {
                //TODO 我的店铺
            }
            default -> {
                commandSender.sendMessage(menu);

            }
        }
        return true;
    }

    final String menu = """
            /create
            /fand
            -
            第1页/共1页
            ------------
            """;
}
