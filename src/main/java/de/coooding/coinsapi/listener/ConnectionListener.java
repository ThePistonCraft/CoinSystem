package de.coooding.coinsapi.listener;

import de.coooding.coinsapi.CoinsAPI;
import de.coooding.coinsapi.utils.CoinsProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * JavaDoc this file!
 * Created: 11.01.2022
 *
 * @author ThePistonCraft (prinzmettwurst@web.de)
 */
public class ConnectionListener
        implements Listener {
    boolean firstJoin = CoinsAPI.getInstance().getConfig().getBoolean("Settings.setJoinCoins");

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CoinsProvider.createCoinPlayer(player);
        if (isFirstJoin()) {
            int amount = CoinsAPI.getInstance().getConfig().getInt("Settings.firstJoinCoins");
            if(player.hasPlayedBefore()) return;
            CoinsProvider.setCoins(player, amount);
            player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.setCoinsByJoin").replace("&", "§")
                    .replace("%amount%", String.valueOf(amount)));
        }
    }

    public boolean isFirstJoin() {
        if (!firstJoin) {
            return false;
        }
        return true;
    }
}
