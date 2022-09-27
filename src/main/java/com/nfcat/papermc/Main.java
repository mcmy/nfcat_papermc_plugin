package com.nfcat.papermc;

import com.nfcat.papermc.commands.*;
import com.nfcat.papermc.commands.money.Money;
import com.nfcat.papermc.commands.user.ChangePass;
import com.nfcat.papermc.commands.user.Login;
import com.nfcat.papermc.commands.user.Register;
import com.nfcat.papermc.data.PlayerData;
import com.nfcat.papermc.listener.NfcatLoginListener;
import com.nfcat.papermc.sql.JdbcDBCP;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends JavaPlugin {

    public static final List<String> forbiddenCommand = Arrays.asList("/op ", "/deop", "/summon ");
    public static final List<PlayerData> playerDataList = new CopyOnWriteArrayList<>();

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

        getServer().getPluginManager().registerEvents(new NfcatLoginListener(), this);

        se("menu", new BiMenu());
        se("nfcat", new BiNfcat());
        se("shop", new BiShop());
        se("bank", new BiBank());
        se("card", new BiCard());
        se("money",new Money());


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
