package com.nfcat.papermc.commands.shop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfcat.papermc.Main;
import com.nfcat.papermc.configs.MybatisConfig;
import com.nfcat.papermc.entity.Shopentity;
import com.nfcat.papermc.entity.Shopobj;
import com.nfcat.papermc.mapper.ShopMapper;
import com.nfcat.papermc.sql.entry.NfMcUser;
import com.nfcat.papermc.sql.manager.UserSQLManager;
import com.nfcat.papermc.utils.MocGUIUtils;
import com.nfcat.papermc.utils.MocShopUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Shop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        switch (strings[0]) {
            case "create" -> {
                NfMcUser nfMcUser = UserSQLManager.selectUser(commandSender.getName());
                if (strings[1].length() == 0) {
                    commandSender.sendMessage("请输入店铺名称");
                    return true;
                }

                if (!MocShopUtils.fandshop(strings[1]).toString().equals("false")){
                    commandSender.sendMessage("店铺名重复或名称不可用");
                    return true;
                }
                switch (strings[2]) {
                    case "水晶" -> {
                        long cshopcrystal = Long.parseLong(Main.configProp.getProperty("cshopcrystal", ""));

                        if (nfMcUser.getCrystal() < cshopcrystal) {
                            commandSender.sendMessage("余额不足");
                            return true;
                        }
                        boolean b = UserSQLManager.addcrystal(commandSender.getName(), -cshopcrystal);
                        if (!b) {
                            commandSender.sendMessage("交易失败，请联系管理员");
                            return true;
                        }

                    }
                    case "金币" -> {

                        long cshopgold = Long.parseLong(Main.configProp.getProperty("cshopgold", ""));
                        if (nfMcUser.getGold() < cshopgold) {
                            commandSender.sendMessage("余额不足");
                            return true;
                        }
                        boolean b = UserSQLManager.addGold(commandSender.getName(), -cshopgold);
                        if (!b) {
                            commandSender.sendMessage("交易失败，请联系管理员");
                            return true;
                        }

                    }
                    default -> {
                        commandSender.sendMessage("请确认支付货币");
                        return true;
                    }
                }

                Shopentity shopentity = new Shopentity();
                shopentity.setShopName(strings[1]);
                shopentity.setMcName(commandSender.getName());
                MocShopUtils.addshop(commandSender, shopentity);


            }
            case "fand" -> {
                Object fandshop = MocShopUtils.fandshop(strings[1]);
                if (fandshop.toString().equals("false")){
                    commandSender.sendMessage("店铺不存在");
                }
                Shopentity shop= (Shopentity) fandshop;
                Inventory itemStacks = MocGUIUtils.create(commandSender, 6 * 9, shop.getShopName());
                //TODO 渲染商品
                return true;

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
            /create name type 创建店铺
            /fand 
            -
            第1页/共1页
            ------------
            """;
}
