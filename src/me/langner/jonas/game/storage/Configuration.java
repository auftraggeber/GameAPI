package me.langner.jonas.game.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles the configuration-storage.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public abstract class Configuration extends YamlConfiguration {

    private File file;

    /**
     * Creates a new instance for a configuration file.
     * @param file The file, where the configuration gets stored.
     */
    public Configuration(File file) {
        this.file = file;

        /* Standarddinge setzen */
        setDefaults();
        options().copyDefaults(true);

        /* laden oder erstellen */
        if (!load())
            save();

        /* Speichern */
        configurations.add(this);
    }

    /**
     * Creates a new instance for a configuration file.
     * @param plugin The plugin, which uses the configuration.
     * @param fileName The name of the configuration.
     */
    public Configuration(Plugin plugin, String fileName) {
        this(new File("plugins/" + plugin.getName() + "/" + fileName));
    }

    /**
     * This method defines all default values.
     */
    public abstract void setDefaults();

    /**
     * Reloads the configuration from the file.
     * @return Returns, if configuration was loaded.
     */
    public boolean load() {
        try {
            load(file);
            return true;
        } catch(Exception ex) {}

        return false;
    }

    /**
     * Store the configuration into the file.
     * @return Returns, if configuration was saved.
     */
    public boolean save() {
        try {
            save(file);
            return true;
        } catch(Exception ex) {}

        return false;
    }

    @Override
    public void set(String path, Object value) {
        super.set(path, value);

        /* Danach speichern */
        save();
    }

    @Override
    public Object get(String path, Object def) {
        /* erst laden, dann lesen */
        load();

        return super.get(path, def);
    }

    private static Set<Configuration> configurations = new HashSet<>();

    public static Set<Configuration> getConfigurations() {
        return Collections.unmodifiableSet(configurations);
    }

}
