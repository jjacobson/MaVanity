package com.mobarenas.mavanity.mysql;

import com.mobarenas.mavanity.MaVanity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by HP1 on 12/23/2015.
 */
public class MySQL {

    private MaVanity plugin;
    private Connection connection;
    private String host;
    private String port;
    private String database;
    private String user;
    private String password;

    public MySQL(MaVanity plugin) throws SQLException {
        this.plugin = plugin;
        loadDatabaseInfo();
        runTask();
        renewConnection();
        checkTables();
    }

    /**
     * Ensure the connection does not time out or fail
     */
    private void runTask() {
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            try {
                checkConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }, 1200, 1200);
    }

    /**
     * Load the database info from the config
     */
    private void loadDatabaseInfo() {
        this.host = plugin.getConfig().getString("mysql.host");
        this.port = plugin.getConfig().getString("mysql.port");
        this.database = plugin.getConfig().getString("mysql.database");
        this.user = plugin.getConfig().getString("mysql.username");
        this.password = plugin.getConfig().getString("mysql.password");
    }

    /**
     * Get the sql connection
     * Check to ensure its still valid
     *
     * @return the sql connection
     */
    public Connection getConnection() {
        try {
            checkConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    /**
     * Check to ensure the sql connection is still valid
     * Reconnect if it fails
     *
     * @throws SQLException
     */
    private void checkConnection() throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute("SELECT 1");
        } catch (SQLException ex) {
            plugin.getLogger().info("MySQL connection failed, attempting to reconnect...");
            renewConnection();
        }
    }

    /**
     * Renew the sql connection
     *
     * @throws SQLException
     */
    private void renewConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ignored) {
        } finally {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
        }
    }

    /**
     * Ensure the tables are correctly set up
     */
    private void checkTables() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Statement st = getConnection().createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS MaVanity (" +
                        "uuid VARCHAR(40) PRIMARY KEY UNIQUE NOT NULL, " +
                        "players_visible BOOLEAN DEFAULT TRUE, " +
                        "pets_visible BOOLEAN DEFAULT TRUE, " +
                        "disguises_visible BOOLEAN DEFAULT TRUE, " +
                        "particles_visible BOOLEAN DEFAULT TRUE, " +
                        "effect_type VARCHAR(20) DEFAULT NULL, " +
                        "effect VARCHAR(20) DEFAULT NULL, " +
                        "color VARCHAR(20) DEFAULT NULL, " +
                        "crates INT NOT NULL DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
