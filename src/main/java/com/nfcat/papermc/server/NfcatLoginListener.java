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
import java.util.Objects;
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

    public static final class KickCallable implements Callable<Boolean> {
        private final Player player;
        private final String string;

        public KickCallable(Player player, String string) {
            this.player = player;
            this.string = string;
        }

        @Override
        public Boolean call() {
            removeNoLoginUser(player);
            player.kick(Component.text(string));
            return true;
        }

        public Player player() {
            return player;
        }

        public String string() {
            return string;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (KickCallable) obj;
            return Objects.equals(this.player, that.player) &&
                    Objects.equals(this.string, that.string);
        }

        @Override
        public int hashCode() {
            return Objects.hash(player, string);
        }

        @Override
        public String toString() {
            return "KickCallable[" +
                    "player=" + player + ", " +
                    "string=" + string + ']';
        }

    }

    public static final class WelcomeCallable implements Callable<Boolean> {
        private final String name;

        public WelcomeCallable(String name) {
            this.name = name;
        }

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

        public String name() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (WelcomeCallable) obj;
            return Objects.equals(this.name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return "WelcomeCallable[" +
                    "name=" + name + ']';
        }

    }
}