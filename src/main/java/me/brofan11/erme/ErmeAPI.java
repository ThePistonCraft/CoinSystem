package me.brofan11.erme;

import me.brofan11.erme.listener.ConnectionListener;
import me.brofan11.erme.sql.MySQL;
import me.brofan11.erme.utils.YamlConfigurationLoader;
import lombok.Getter;
import me.brofan11.erme.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * JavaDoc this file!
 * Created: 10.01.2022
 *
 * @author ThePistonCraft (prinzmettwurst@web.de)
 */
public class ErmeAPI
        extends JavaPlugin {

    @Getter
    public static ErmeAPI instance;
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

        getCommand("erme").setExecutor(new CoinsCommand());
        getCommand("seterme").setExecutor(new SetCoinsCommand());
        getCommand("adderme").setExecutor(new AddCoinsCommand());
        getCommand("removeerme").setExecutor(new RemoveCoinsCommand());
        getCommand("payerme").setExecutor(new PayCommand());
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }
}
