package network.darkhelmet.prism.database;

import network.darkhelmet.prism.Prism;
import network.darkhelmet.prism.database.mysql.MySqlPrismDataSource;
import network.darkhelmet.prism.database.mysql.PrismHikariDataSource;
import network.darkhelmet.prism.database.sql.SqlPrismDataSource;
import network.darkhelmet.prism.database.sql.SqlPrismDataSourceUpdater;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;

public class PrismDatabaseFactory {
    private static PrismDataSource database = null;

    /**
     * Create a config.
     * 
     * @param configuration ConfigurationSection
     */
    public static void createDefaultConfig(final ConfigurationSection configuration) {
        ConfigurationSection dataSourceSection;
        ConfigurationSection dataSourceProperties;

        if (configuration.isConfigurationSection("datasource")) {
            dataSourceSection = configuration.getConfigurationSection("datasource");
            dataSourceSection.addDefault("type", "mysql");

            if (!dataSourceSection.isConfigurationSection("properties")) {
                dataSourceProperties = dataSourceSection.createSection("properties");
            } else {
                dataSourceProperties = dataSourceSection.getConfigurationSection("properties");
            }
        } else {
            String type = configuration.getString("datasource");// gets the old datasource.
            dataSourceSection = configuration.createSection("datasource");

            if (type != null) {
                dataSourceSection.set("type", type);
            } else {
                dataSourceSection.addDefault("type", "mysql");
            }
            dataSourceProperties = dataSourceSection.createSection("properties");
        }

        String dataType = dataSourceSection.getString("type", "mysql");

        updateDataSourceProperties(dataType, dataSourceProperties);
        addDatabaseDefaults(configuration);
    }

    private static void updateDataSourceProperties(@Nullable final String type,
            final ConfigurationSection configuration) {
        String test = type;

        if (test == null) {
            test = "mysql";
        }

        switch (test) {
            case "mysql":
                MySqlPrismDataSource.updateDefaultConfig(configuration);
                break;
            case "hikari":
            default:
                SqlPrismDataSource.updateDefaultConfig(configuration);
        }
    }

    private static void addDatabaseDefaults(ConfigurationSection section) {
        upgradeEntry(section, "query.max-failures-before-wait", "prism.query.max-failures-before-wait", 3);
        upgradeEntry(section, "query.actions-per-insert-batch", "prism.query.actions-per-insert-batch", 1000);
        upgradeEntry(section, "query.force-write-queue-on-shutdown", "prism.query.force-write-queue-on-shutdown", true);
        upgradeEntry(section, "prism.queue-empty-tick-delay", "prism.query.queue-empty-tick-delay", 3);
    }

    private static void upgradeEntry(ConfigurationSection section, String oldPath, String newPath, Object def) {
        Object old = section.get(oldPath);

        section.set(oldPath, null);
        section.addDefault(newPath, old == null ? def : old);
    }

    /**
     * Constuct Data source.
     * 
     * @param configuration ConfigurationSection
     * @return PrismDataSource
     */
    public static PrismDataSource createDataSource(ConfigurationSection configuration) {
        if (configuration == null) {
            return null;
        }

        String dataSource;
        ConfigurationSection dataSourceProperties;

        if (configuration.isConfigurationSection("datasource")) {
            ConfigurationSection dataSourceSection = configuration.getConfigurationSection("datasource");

            if (dataSourceSection != null) { // in case they didnt update the config.
                dataSource = dataSourceSection.getString("type");
                dataSourceProperties = dataSourceSection.getConfigurationSection("properties");
            } else {
                // old config style
                dataSource = configuration.getString("datasource");
                dataSourceProperties = configuration.getConfigurationSection("prism." + dataSource);
            }
        } else {
            // old config style
            dataSource = configuration.getString("datasource");
            dataSourceProperties = configuration.getConfigurationSection("prism." + dataSource);
        }

        if (dataSource == null) {
            return null;
        }

        switch (dataSource) {
            case "mysql":
                Prism.log("Attempting to configure datasource as MySQL.");
                database = new MySqlPrismDataSource(dataSourceProperties);
                break;
            case "hikari":
                Prism.log("Attempting to configure datasource using the Hikari parameters.");
                database = new PrismHikariDataSource(dataSourceProperties);
                break;
            default:
                Prism.warn("ERROR: This version of Prism does not support " + dataSource);
                break;
        }

        return database;
    }

    /**
     * Create updater for datasource.
     * 
     * @param configuration ConfigurationSection
     * @return PrismDataSourceUpdater
     */
    public static PrismDataSourceUpdater createUpdater(ConfigurationSection configuration) {
        if (configuration == null) {
            return null;
        }

        String dataSource = configuration.getString("type", "mysql");

        if (dataSource == null) {
            return null;
        }

        switch (dataSource) {
            case "mysql":
            case "derby":
            case "sqlite":
                return new SqlPrismDataSourceUpdater(database);
            default:
                return null;
        }
    }

    public static Connection getConnection() {
        return database.getConnection();
    }
}
