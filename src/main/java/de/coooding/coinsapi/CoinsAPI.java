package de.coooding.coinsapi;

import de.coooding.coinsapi.commands.*;
import de.coooding.coinsapi.listener.ConnectionListener;
import de.coooding.coinsapi.sql.MySQL;
import de.coooding.coinsapi.utils.YamlConfigurationLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * JavaDoc this file!
 * Created: 10.01.2022
 *
 * @author ThePistonCraft (prinzmettwurst@web.de)
 */
public class CoinsAPI
        extends JavaPlugin {

    @Getter
    public static CoinsAPI instance;
    @Getter
    public String prefix = getConfig().getString("Messages.prefix").replaceAll("&", "§");

    @Override
    public void onEnable() {
        Bukkit.broadcastMessage("§aThe CoinSystem with API was created by §eCoooding");
        Bukkit.getConsoleSender().sendMessage("§aThe Support §bDiscord§7: §ehttps://discord.gg/8WjfsxZEzX");
        instance = this;
        this.init();
    }
    private void init() {
        YamlConfigurationLoader.loadConfiguration(this, "config.yml");

        MySQL.connect();
        MySQL.createTable();

        getCommand("coins").setExecutor(new CoinsCommand());
        getCommand("setcoins").setExecutor(new SetCoinsCommand());
        getCommand("addcoins").setExecutor(new AddCoinsCommand());
        getCommand("removecoins").setExecutor(new RemoveCoinsCommand());
        getCommand("pay").setExecutor(new PayCommand());
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }
}
