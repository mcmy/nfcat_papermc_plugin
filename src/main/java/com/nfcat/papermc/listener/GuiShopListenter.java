package com.nfcat.papermc.listener;

import com.nfcat.papermc.configs.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuiShopListenter implements Listener {
    @EventHandler
    public void guilister(InventoryClickEvent e){
        e.setCancelled(true);
    }
}
