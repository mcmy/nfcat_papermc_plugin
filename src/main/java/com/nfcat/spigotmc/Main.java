package com.nfcat.spigotmc;

import com.nfcat.spigotmc.commands.*;
import com.nfcat.spigotmc.config.Config;
import com.nfcat.spigotmc.server.NfcatListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class Main extends JavaPlugin {

    public static Config config;

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
        getLogger().info("Welcome use nfcat plugin,init plugin");
        try (InputStream content = this.getClass().getClassLoader().getResourceAsStream("config.yml")) {
            Yaml yaml = new Yaml(new Constructor(Config.class));
            config = yaml.load(content);
        } catch (Exception ex) {
            getLogger().info("load nfcat plugin error");
            throw new RuntimeException(ex);
        }
        se("helpme", new HelpMe());
        se("nfcat", new Nfcat());
        se("login", new Login());
        se("register", new Register());
        se("changepass", new ChangePass());
        getServer().getPluginManager().registerEvents(new NfcatListener(), this);
    }

    private void se(String command, CommandExecutor executor) {
        PluginCommand help = Bukkit.getPluginCommand(command);
        if (help != null) help.setExecutor(executor);
    }
}
