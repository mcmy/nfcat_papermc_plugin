package com.nfcat.papermc.data;

import com.nfcat.papermc.utils.NfUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public final class PlayerData {
    private String username;
    private Player player;
    private LocalDateTime lastUseCard;

    public LocalDateTime getLastUseCard() {
        if (lastUseCard == null) return LocalDateTime.MIN;
        return lastUseCard;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PlayerData playerData) {
            if (playerData == this) return true;
            return NfUtils.isEquals(playerData.getUsername(), username);
        }
        return false;
    }

}
