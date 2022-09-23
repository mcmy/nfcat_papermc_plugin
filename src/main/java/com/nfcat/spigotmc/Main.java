package com.nfcat.spigotmc;

import com.nfcat.spigotmc.commands.*;
import com.nfcat.spigotmc.server.NfcatListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        getLogger().info("Welcome use nfcat plugin");
        se("helpme", new HelpMe());
        se("nfcat", new Nfcat());
        se("login", new Login());
        se("register", new Register());
        se("changepass", new ChangePass());
        getServer().getPluginManager().registerEvents(new NfcatListener(), this);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {

    }

    private void se(String command, CommandExecutor executor) {
        PluginCommand help = Bukkit.getPluginCommand(command);
        if (help != null) help.setExecutor(executor);
    }
}
