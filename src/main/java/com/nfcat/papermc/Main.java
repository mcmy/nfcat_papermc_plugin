package com.nfcat.papermc;

import com.nfcat.papermc.commands.menu.Menu;
import com.nfcat.papermc.listener.DisableInstructionListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.util.Properties;

public class Main extends JavaPlugin {

    public static Properties configProp = new Properties();
    public static JavaPlugin plugin;

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
        plugin = this;
        getLogger().info("start init nfcat plugin,init plugin");

        PluginManager pluginManager = getServer().getPluginManager();
        try (InputStream ci = getClassLoader().getResourceAsStream("config.properties")) {
            configProp.load(ci);
        } catch (Exception ex) {
            pluginManager.disablePlugin(this);
            throw new RuntimeException(ex);
        }

        pluginManager.registerEvents(new DisableInstructionListener(), this);

        se("menu", new Menu());

        getLogger().info("Welcome use nfcat plugin");
    }

    private void se(String command, CommandExecutor executor) {
        PluginCommand help = getCommand(command);
        if (help == null) getLogger().info("no " + command);
        if (help != null) help.setExecutor(executor);
    }
}
