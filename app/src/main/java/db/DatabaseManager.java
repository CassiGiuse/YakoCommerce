package db;

import utils.EnvLoader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DatabaseManager {
    private static HikariDataSource dataSource;

    static {
        final EnvLoader envLoader = new EnvLoader("/db");

        final String dbUrl = envLoader.get("DB_URL");
        final String dbUser = envLoader.get("DB_USER");
        final String dbPassword = envLoader.get("DB_PASS");

        if (dbUrl == null || dbUser == null || dbPassword == null) {
            final String errorMsg = "Variabili d'ambiente mancanti! Assicurarsi di impostare correttamente le seguenti variabili: DB_URL, DB_USER e DB_PASSWORD.";
            throw new RuntimeException(
                    errorMsg);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(600000);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}