package com.nfcat.papermc.commands.shop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfcat.papermc.configs.MybatisConfig;
import com.nfcat.papermc.entity.Shopobj;
import com.nfcat.papermc.mapper.ShopMapper;
import com.nfcat.papermc.utils.MocGUIUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShopList implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        return false;

    }
}
