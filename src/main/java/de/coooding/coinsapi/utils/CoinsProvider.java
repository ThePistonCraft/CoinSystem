package de.coooding.coinsapi.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import de.coooding.coinsapi.sql.MySQL;
import org.bukkit.entity.Player;

/**
 * JavaDoc this file!
 * Created: 10.01.2022
 *
 * @author ThePistonCraft (prinzmettwurst@web.de)
 */
public class CoinsProvider {
    public static int getPlayerCoins(Player player) {
        String uuid = player.getUniqueId().toString();
        if (isPlayerInDatabase(player)) {
            try {
                PreparedStatement ps = MySQL.getStatement("SELECT * FROM playerCoins WHERE UUID= ?");
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                rs.next();
                Integer coins = rs.getInt("coins");
                rs.close();
                ps.close();
                return coins;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return 0;
        } else {
            createCoinPlayer(player);
        }
        return 0;
    }

    public static boolean isPlayerInDatabase(Player player) {
        String uuid = player.getUniqueId().toString();
        try {
            PreparedStatement ps = MySQL.getStatement("SELECT * FROM playerCoins WHERE UUID= ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            boolean user = rs.next();
            rs.close();
            ps.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void createCoinPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        int coins = 0;
        try {
            PreparedStatement ps = MySQL.getStatement("INSERT INTO playerCoins (UUID, Coins) VALUES (?, ?)");
            ps.setString(1, uuid);
            ps.setInt(2, coins);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCoins(Player player, Integer add) {
        String uuid = player.getUniqueId().toString();
        Integer coinsBefore = getPlayerCoins(player);
        try {
            PreparedStatement ps = MySQL.getStatement("UPDATE playerCoins SET Coins=? WHERE UUID=?");

            ps.setInt(1, coinsBefore + add);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeCoins(Player player, Integer amount) {
        String uuid = player.getUniqueId().toString();
        Integer coinsBefore = getPlayerCoins(player);
        if (coinsBefore < 0) {
            amount = 0;
            System.out.println("[Coins] Player have already 0 Coins.");

            return;
        }
        try {
            PreparedStatement ps = MySQL.getStatement("UPDATE playerCoins SET Coins=? WHERE UUID=?");

            ps.setInt(1, coinsBefore - amount);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setCoins(Player player, Integer set) {
        String uuid = player.getUniqueId().toString();
        if (set < 0) {
            return;
        }
        try {
            PreparedStatement ps = MySQL.getStatement("UPDATE playerCoins SET Coins=? WHERE UUID=?");

            ps.setInt(1, set);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
