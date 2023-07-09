package com.ensaf.facturation.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionPool {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    static {
    	AppProperties appConfig = AppProperties.getInstance();
    	config.setDriverClassName(appConfig.getProperty("datasource.driver"));
        config.setJdbcUrl(appConfig.getProperty("datasource.url"));
        config.setUsername(appConfig.getProperty("datasource.user"));
        config.setPassword(appConfig.getProperty("datasource.password"));
        dataSource = new HikariDataSource(config);
    }

    private DatabaseConnectionPool() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
