package me.brofan11.erme.listener;

import me.brofan11.erme.ErmeAPI;
import me.brofan11.erme.utils.CoinsProvider;
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
    boolean firstJoin = ErmeAPI.getInstance().getConfig().getBoolean("Settings.setJoinCoins");

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CoinsProvider.createCoinPlayer(player);
        if (isFirstJoin()) {
            int amount = ErmeAPI.getInstance().getConfig().getInt("Settings.firstJoinCoins");
            if(player.hasPlayedBefore()) return;
            CoinsProvider.setCoins(player, amount);
            player.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.setCoinsByJoin").replace("&", "§")
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
