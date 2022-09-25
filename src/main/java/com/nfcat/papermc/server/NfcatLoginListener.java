package com.nfcat.papermc.server;

import com.nfcat.papermc.Main;
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
            3, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(10));

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerJoinEvent event) {
        event.getPlayer().showTitle(
                Title.title(Component.text("服务器官网:nfcat.com"),
                        Component.text("登录:/l <密码> 注册:/r <密码> <重复密码>"),
                        Title.Times.times(
                                Duration.ofSeconds(1),
                                Duration.ofSeconds(60),
                                Duration.ofSeconds(1))));
        noLoginUser.put(event.getPlayer().getName(),
                new Dt(event.getPlayer(), event.getPlayer().getGameMode()));
        event.getPlayer().setGameMode(GameMode.SPECTATOR);

        pool.execute(new LoginRunnable(event));
    }

    public static void removeNoLoginUser(Player player) {
        Dt dt = noLoginUser.get(player.getName());
        player.setGameMode(dt.getGameMode());
        noLoginUser.remove(player.getName());
    }

    @EventHandler
    public void p0(PlayerMoveEvent event) {
        if (noLoginUser.containsKey(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void p1(PlayerQuitEvent event) {
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
        if (event.getPlayer().isOp()) {
            for (String s : Main.forbiddenCommand) {
                if (msg.startsWith(s)) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("服务器已禁止使用此命令");
                    return;
                }
            }
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
    }

    static class LoginRunnable implements Runnable {
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
                    if (i >= 30 && noLoginUser.containsKey(username)) {
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