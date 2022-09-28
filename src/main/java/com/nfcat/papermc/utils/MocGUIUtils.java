package com.nfcat.papermc.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

public class MocGUIUtils {
    /**
     * 创建gui页面
     *
     * @param sender
     * @param size
     * @param title
     * @return
     */
    public static Inventory create(CommandSender sender, int size, String title){
        Inventory gui = Bukkit.createInventory(null, size, Component.text(title));
        return gui;
    }

    /**
     * 在gui中添加物品
     * @param gui
     * @param num
     * @param name
     */
    public static void adddiyname(Inventory gui,CommandSender sender,int num,@Nullable String name){
        Player p= (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        gui.setItem(num, item);//添加到gui
    }
    public static void add(Inventory gui,CommandSender sender,int num){
        Player p= (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        gui.setItem(num, item);//添加到gui
    }
}
