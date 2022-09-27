package com.nfcat.papermc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class DisableInstructionListener implements Listener {

    public static final List<String> forbiddenCommand = Arrays.asList("/op ", "/deop", "/summon ");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void p2(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage();
        if (event.getPlayer().isOp()) {
            for (String s : forbiddenCommand) {
                if (msg.startsWith(s)) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("服务器已禁止使用此命令");
                    return;
                }
            }
        }
    }

}
