package com.nfcat.papermc.thread.callback;

import org.bukkit.Bukkit;

import java.util.concurrent.Callable;

public record McWorld(String param) implements Callable<Boolean> {

    @Override
    public Boolean call() {
        System.out.println(param);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon tnt");
        return true;
    }
}
