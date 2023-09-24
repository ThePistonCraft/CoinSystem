package me.brofan11.erme.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import me.brofan11.erme.sql.MySQL;
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
                PreparedStatement ps = MySQL.getStatement("SELECT * FROM ermetabla WHERE UUID= ?");
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                rs.next();
                Integer erme = rs.getInt("erme");
                rs.close();
                ps.close();
                return erme;
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
            PreparedStatement ps = MySQL.getStatement("SELECT * FROM ermetabla WHERE UUID= ?");
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
        int erme = 0;
        try {
            PreparedStatement ps = MySQL.getStatement("INSERT INTO ermetabla (UUID, Erme) VALUES (?, ?)");
            ps.setString(1, uuid);
            ps.setInt(2, erme);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCoins(Player player, Integer add) {
        String uuid = player.getUniqueId().toString();
        Integer ermeBefore = getPlayerCoins(player);
        try {
            PreparedStatement ps = MySQL.getStatement("UPDATE ermetabla SET Erme=? WHERE UUID=?");

            ps.setInt(1, ermeBefore + add);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeCoins(Player player, Integer amount) {
        String uuid = player.getUniqueId().toString();
        Integer ermeBefore = getPlayerCoins(player);
        if (ermeBefore < 0) {
            amount = 0;
            System.out.println("[Coins] Player have already 0 Coins.");

            return;
        }
        try {
            PreparedStatement ps = MySQL.getStatement("UPDATE ermetabla SET Erme=? WHERE UUID=?");

            ps.setInt(1, ermeBefore - amount);
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
            PreparedStatement ps = MySQL.getStatement("UPDATE ermetabla SET Erme=? WHERE UUID=?");

            ps.setInt(1, set);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
