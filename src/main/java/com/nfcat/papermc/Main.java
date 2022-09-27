package com.nfcat.papermc;

import com.nfcat.papermc.commands.bank.Bank;
import com.nfcat.papermc.commands.bank.Money;
import com.nfcat.papermc.commands.card.Card;
import com.nfcat.papermc.commands.cloud.Nfcat;
import com.nfcat.papermc.commands.menu.Menu;
import com.nfcat.papermc.commands.shop.GuiShop;
import com.nfcat.papermc.commands.shop.Shop;
import com.nfcat.papermc.commands.user.ChangePass;
import com.nfcat.papermc.commands.user.Login;
import com.nfcat.papermc.commands.user.Register;
import com.nfcat.papermc.data.PlayerData;
import com.nfcat.papermc.listener.DisableInstructionListener;
import com.nfcat.papermc.listener.NfcatLoginListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends JavaPlugin {

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

        PluginManager pluginManager = getServer().getPluginManager();
        try (InputStream ci = getClassLoader().getResourceAsStream("config.properties")) {
            configProp.load(ci);
        } catch (Exception ex) {
            pluginManager.disablePlugin(this);
            throw new RuntimeException(ex);
        }

        pluginManager.registerEvents(new NfcatLoginListener(), this);
        pluginManager.registerEvents(new DisableInstructionListener(), this);

        se("menu", new Menu());
        se("nfcat", new Nfcat());
        se("shop", new Shop());
        se("bank", new Bank());
        se("card", new Card());
        se("money", new Money());
        se("openguishop",new GuiShop());

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
