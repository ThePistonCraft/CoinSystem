package de.coooding.coinsapi.commands;

import de.coooding.coinsapi.CoinsAPI;
import de.coooding.coinsapi.utils.CoinsProvider;
import de.coooding.coinsapi.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 10.01.2022
 *
 * @author ThePistonCraft (prinzmettwurst@web.de)
 */
public class CoinsCommand
        implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(args.length != 1) {
            player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.haveCoins")
                    .replace("%coins%", String.valueOf(CoinsProvider.getPlayerCoins(player))).replace("&", "§"));
            return true;
        }
        String target = args[0];
        Player player1 = Bukkit.getPlayer(target);
        if(player1 == null) {
            player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.targetIsNull")
                    .replace("&", "§"));
            return true;
        }
        player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.targetCoins")
                .replace("%coins%", String.valueOf(CoinsProvider.getPlayerCoins(player1))).replace("%targetPlayer%", player1.getName())
                .replace("&", "§"));

        return false;
    }
}
