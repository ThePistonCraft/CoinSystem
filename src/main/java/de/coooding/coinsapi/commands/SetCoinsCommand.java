package de.coooding.coinsapi.commands;

import de.coooding.coinsapi.CoinsAPI;
import de.coooding.coinsapi.utils.CoinsProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * JavaDoc this file!
 * Created: 10.01.2022
 *
 * @author ThePistonCraft (prinzmettwurst@web.de)
 */
public class SetCoinsCommand
        implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(!(player.hasPermission("coinsapi.setcoins")) || !(player.hasPermission("coinsapi.administrator"))) {
            player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.noPermission")
                    .replace("&", "§"));
            return true;
        }
        if(args.length != 2) {
            player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.commandSyntaxHelp")
                    .replace("&", "§").replace("%commandSyntax%", "/setcoins <player> <amount>"));
            return true;
        }
        String target = args[0];
        try {
            int amount = Integer.parseInt(args[1]);
            Player player1 = Bukkit.getPlayer(target);
            if(player1 == null) {
                player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.targetIsNull")
                        .replace("&", "§"));
                return true;
            }
            if(CoinsProvider.isPlayerInDatabase(player1)) {
                CoinsProvider.setCoins(player1, amount);
                player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.setPlayerCoins")
                        .replace("%targetPlayer%", player1.getName()).replace("%amount%", String.valueOf(amount)).replace("&", "§"));
            } else {
                player.sendMessage(CoinsAPI.getInstance().getPrefix() + CoinsAPI.getInstance().getConfig().getString("Messages.playerIsNotInDatabase")
                        .replace("&", "§"));
            }
        }catch (NumberFormatException ignored) {

        }

        return false;
    }
}
