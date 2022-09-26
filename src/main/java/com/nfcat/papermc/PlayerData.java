package com.nfcat.papermc;

import lombok.Data;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

@Data
public class PlayerData {
    private String username;
    private Player player;
    private LocalDateTime lastUseCard;

    public LocalDateTime getLastUseCard() {
        if (lastUseCard == null) return LocalDateTime.MIN;
        return lastUseCard;
    }
}
