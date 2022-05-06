package com.sayurbox.sequel2api.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sayurbox.sequel2api.app.config.Database;
import com.sayurbox.sequel2api.app.config.Root;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
@ConditionalOnClass(Root.class)
@ComponentScan(basePackages="com.sayurbox.sequel2api.app")
@EnableConfigurationProperties(SequelApiProperties.class)
public class Sequel2ApiAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(Sequel2ApiAutoConfiguration.class);

    @Autowired
    private SequelApiProperties sequelApiProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    public Root provideConfiguration() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        Root root = mapper.readValue(
                new File(sequelApiProperties.getYamlLocation()), Root.class);
        logger.info("loaded root configuration {}", root);
        return root;
    }

    @Bean(name = "sqlJdbcTemplate")
    @ConditionalOnMissingBean
    public NamedParameterJdbcTemplate jdbcTemplate(Root root) {
        Database db = root.getDatabase();
        if (db.getOverrideDataSource() != null) {
            DataSource ds = applicationContext.getBean("dataSource", DataSource.class);
            logger.info("Override DataSource from parent {}", ds);
            return new NamedParameterJdbcTemplate(ds);
        }
        HikariConfig hikariConfig = new HikariConfig();
        String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s?autoReconnect=true&useSSL=false",
                db.getHostname(),
                db.getPort(),
                db.getName());
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(db.getUsername());
        hikariConfig.setPassword(db.getPassword());
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true" );
        hikariConfig.addDataSourceProperty("prepStmtCacheSize" , "250" );
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        DataSource dataSource = new HikariDataSource(hikariConfig);
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
