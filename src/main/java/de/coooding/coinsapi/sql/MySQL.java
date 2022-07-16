package de.coooding.coinsapi.sql;

import de.coooding.coinsapi.CoinsAPI;

import java.sql.*;

/**
 * JavaDoc this file!
 * Created: 10.01.2022
 *
 * @author ThePistonCraft (prinzmettwurst@web.de)
 */
public class MySQL {

    public static String host = CoinsAPI.getInstance().getConfig().getString("MySQL.host");
    public static String database = CoinsAPI.getInstance().getConfig().getString("MySQL.database");
    public static String user = CoinsAPI.getInstance().getConfig().getString("MySQL.username");
    public static String passwd = CoinsAPI.getInstance().getConfig().getString("MySQL.password");
    public static String port = CoinsAPI.getInstance().getConfig().getString("MySQL.port");
    public static Connection connection;

    public static void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + "3306/" + database + "?autoReconnect=true", user, passwd);
            } catch (SQLException ignored) {
                System.out.println("[CoinsMySQL] MySQL konnte nicht aufgebaut werden.");
            }
        }
    }

    public static void createTable() {
        try {
            if (MySQL.isConnected()) {
                PreparedStatement createcoinsifnotexists = MySQL
                        .getStatement("CREATE TABLE IF NOT EXISTS playerCoins (UUID VARCHAR(255), coins BIGINT(255))");
                createcoinsifnotexists.executeUpdate();
            }
        } catch (SQLException ignored) { }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                System.out.println("[CoinsMySQL] Verbindung geschlossen.");
            } catch (SQLException ignored) { }
        }

    }

    public static boolean isConnected() {
        return (connection == null ? false : true);

    }

    public static PreparedStatement getStatement(String sql) {
        if (isConnected()) {
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement(sql);
                return ps;
            } catch (SQLException ignored) { }
        }
        return null;
    }

    public static ResultSet getResult(String sql) {
        if (isConnected()) {
            PreparedStatement ps;
            ResultSet rs;
            try {
                ps = getStatement(sql);
                rs = ps.executeQuery();
                return rs;
            } catch (SQLException ignored) { }
        }
        return null;
    }

}
