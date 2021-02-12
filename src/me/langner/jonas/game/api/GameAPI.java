package me.langner.jonas.game.api;

import me.langner.jonas.game.storage.Configuration;
import me.langner.jonas.game.storage.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

/**
 * Plugin.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public class GameAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        /* Verbindungen schließen */
        closeSQLConnections();
        saveAllConfigurations();

        super.onDisable();
    }

    /**
     * Closes all connections to the database.
     */
    private void closeSQLConnections() {
        for (MySQL sql : MySQL.getMySQLSet()) {
            try {
                sql.disconnect();
            } catch(SQLException ex) {}
        }
    }

    /**
     * Saves all configurations.
     */
    private void saveAllConfigurations() {
        Configuration.getConfigurations().forEach(Configuration::save);
    }
}
