package com.nfcat.papermc.commands.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class GuiShop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Inventory gui = Bukkit.createInventory(null, 6 * 9, Component.text("shop"));
        ItemStack domain = new ItemStack(Material.DIAMOND, 10);
        ItemMeta itemMeta = domain.getItemMeta();
        itemMeta.setDisplayName("商品列表");
        domain.setItemMeta(itemMeta);
        gui.setItem(10, domain);
        Player p = (Player) sender;
        p.openInventory(gui);
        return true;
    }
}
