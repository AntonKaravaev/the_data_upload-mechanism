package com.example.dbupdater.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.Properties;

@Configuration
public class SpringJdbcConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver}")
    private String dbDriver;

    @Value("${spring.datasource.database-name}")
    private String dbName;

    @Bean
    public DataSource postgresDataSource() {
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", dbUsername);
        props.setProperty("dataSource.password", dbPassword);
        props.setProperty("dataSource.databaseName", dbName);
        props.put("dataSource.logWriter", new PrintWriter(System.out));

        HikariConfig config = new HikariConfig(props);
        config.setMinimumIdle(3);
        config.setMaximumPoolSize(10);

        HikariDataSource dataSource = new HikariDataSource(config);
        System.out.println("Minimum idle connections: " + dataSource.getMinimumIdle());
        System.out.println("Maximum pool size: " + dataSource.getMaximumPoolSize());
        return dataSource;

    }
}
