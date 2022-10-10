package com.nfcat.papermc.listener;

import com.nfcat.papermc.Main;
import com.nfcat.papermc.data.PlayerData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public final class NfcatLoginListener implements Listener {

    public static final Map<String, Dt> noLoginUser = new ConcurrentHashMap<>();

    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(
            3, 15,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(15));

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (pool.getTaskCount() > 15) {
            player.kick(Component.text("当前未登录人数过多，请稍后重试"));
            noLoginUser.forEach((s, dt) -> removeNoLoginUser(dt.getPlayer()));
        }
        Main.playerDataList.add(new PlayerData()
                .setPlayer(player)
                .setUsername(player.getName()));
        player.setGameMode(GameMode.SPECTATOR);
        LoginRunnable loginRunnable = new LoginRunnable(event);
        noLoginUser.put(player.getName(), new Dt(player, player.getGameMode(), loginRunnable));
        pool.execute(loginRunnable);
        player.showTitle(
                Title.title(Component.text("服务器官网:nfcat.com"),
                        Component.text("登录:/l <密码> 注册:/r <密码> <重复密码>"),
                        Title.Times.times(
                                Duration.ofSeconds(1),
                                Duration.ofSeconds(60),
                                Duration.ofSeconds(1))));
    }

    public static void removeNoLoginUser(Player player) {
        Dt dt = noLoginUser.get(player.getName());
        pool.remove(dt.getLoginRunnable());
        player.setGameMode(dt.getGameMode());
        noLoginUser.remove(player.getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void p0(PlayerMoveEvent event) {
        if (noLoginUser.containsKey(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void p1(PlayerQuitEvent event) {
        Main.playerDataList.remove(new PlayerData().setUsername(event.getPlayer().getName()));
        if (noLoginUser.containsKey(event.getPlayer().getName())) {
            removeNoLoginUser(event.getPlayer());
        }
    }

    static final List<String> canUseCommand = Arrays.asList("/l ", "/r ", "/log ", "/reg ", "/login ", "/register ");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void p2(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage();
        if (event.getPlayer().getGameMode() == GameMode.SPECTATOR
                && noLoginUser.containsKey(event.getPlayer().getName())) {
            for (String s : canUseCommand) {
                if (msg.startsWith(s)) return;
            }
            event.setCancelled(true);
        }
    }

    public static void loginFail(Player player, String info) {
        Bukkit.getScheduler().callSyncMethod(Main.plugin, new KickCallable(player, info));
    }

    public static void loginSuccess(String name) {
        Bukkit.getScheduler().callSyncMethod(Main.plugin, new WelcomeCallable(name));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Dt {
        public Player player;
        public GameMode gameMode;
        public LoginRunnable loginRunnable;
    }

    static final class LoginRunnable implements Runnable {
        final PlayerEvent event;
        final String username;

        public LoginRunnable(PlayerEvent event) {
            this.event = event;
            this.username = event.getPlayer().getName();
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i <= 30; i++) {
                    if (noLoginUser.containsKey(username)) {
                        event.getPlayer().setGameMode(GameMode.SPECTATOR);
                    } else {
                        break;
                    }
                    if (i == 30 && noLoginUser.containsKey(username)) {
                        loginFail(event.getPlayer(), "登录超时");
                        break;
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                loginFail(event.getPlayer(), e.getMessage());
            }
        }
    }

    public record KickCallable(Player player, String string) implements Callable<Boolean> {

        @Override
        public Boolean call() {
            removeNoLoginUser(player);
            player.kick(Component.text(string));
            return true;
        }
    }

    public record WelcomeCallable(String name) implements Callable<Boolean> {

        @Override
        public Boolean call() {
            Player player = Bukkit.getPlayer(name);
            if (player == null) return false;
            removeNoLoginUser(player);
            player.showTitle(
                    Title.title(Component.text(""),
                            Component.text("欢迎回家：" + player.getName() + " 输入/m 打开菜单"),
                            Title.Times.times(
                                    Duration.ofSeconds(1),
                                    Duration.ofSeconds(2),
                                    Duration.ofSeconds(1))));
            return true;
        }
    }
}