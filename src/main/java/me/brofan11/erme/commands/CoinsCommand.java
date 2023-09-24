package me.brofan11.erme.commands;

import me.brofan11.erme.ErmeAPI;
import me.brofan11.erme.utils.CoinsProvider;
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
public class CoinsCommand
        implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(args.length != 1) {
            player.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.haveCoins")
                    .replace("%coins%", String.valueOf(CoinsProvider.getPlayerCoins(player))).replace("&", "§"));
            return true;
        }
        String target = args[0];
        Player player1 = Bukkit.getPlayer(target);
        if(player1 == null) {
            player.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.targetIsNull")
                    .replace("&", "§"));
            return true;
        }
        player.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.targetCoins")
                .replace("%coins%", String.valueOf(CoinsProvider.getPlayerCoins(player1))).replace("%targetPlayer%", player1.getName())
                .replace("&", "§"));

        return false;
    }
}
