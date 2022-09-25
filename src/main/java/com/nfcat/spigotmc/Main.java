package com.nfcat.spigotmc;

import com.nfcat.spigotmc.commands.*;
import com.nfcat.spigotmc.commands.user.ChangePass;
import com.nfcat.spigotmc.commands.user.Login;
import com.nfcat.spigotmc.commands.user.Register;
import com.nfcat.spigotmc.server.NfcatLoginListener;
import com.nfcat.spigotmc.sql.JdbcDBCP;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
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
        getLogger().info("start init nfcat plugin,init plugin");

        try (InputStream ci = getClassLoader().getResourceAsStream("config.properties");
             InputStream di = getClassLoader().getResourceAsStream("dbcp.properties")) {
            configProp.load(ci);
            Properties dbcpProp = new Properties();
            dbcpProp.load(di);
            JdbcDBCP.init(dbcpProp);
        } catch (Exception ex) {
            getServer().getPluginManager().disablePlugin(this);
            throw new RuntimeException(ex);
        }

        plugin = this;
        getServer().getPluginManager().registerEvents(new NfcatLoginListener(), this);

        se("m", new BiMenu());
        se("nf", new BiNfcat());
        se("shop", new BiShop());
        se("bank", new BiBank());

        se("login", new Login());
        se("register", new Register());
        se("changepass", new ChangePass());

        getLogger().info("Welcome use nfcat plugin");
    }

    private void se(String command, CommandExecutor executor) {
        PluginCommand help = getCommand(command);
        if (help == null) getLogger().info("no " + command);
        if (help != null) help.setExecutor(executor);
    }


}
