package com.nfcat.spigotmc.server;

import com.nfcat.spigotmc.exception.VerifyException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public final class NfcatListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onLogin(PlayerLoginEvent event) {
//        event.disallow(PlayerLoginEvent.Result.KICK_OTHER,"验证失败");
        Bukkit.broadcastMessage("welcome: " + event.getPlayer().getName());
        Player player = event.getPlayer();
        String name = player.getName();
        try {
            //TODO 验证登录操作
            throw new VerifyException("没有写这个模块，先踢出等等吧");
        } catch (VerifyException e) {
            event.setKickMessage("登录失败：" + e.getMessage());
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}