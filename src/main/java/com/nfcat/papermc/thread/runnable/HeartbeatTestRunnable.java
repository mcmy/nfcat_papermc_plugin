package com.nfcat.papermc.thread.runnable;

import com.nfcat.papermc.Main;
import com.nfcat.papermc.thread.callback.McWorld;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class HeartbeatTestRunnable extends BukkitRunnable {

    // new Thread(new HeartbeatTestRunnable()).start();
    @Override
    public void run() {
        Bukkit.getScheduler().callSyncMethod(Main.plugin, new McWorld("测试线程传入主线程参数"));
    }
}
