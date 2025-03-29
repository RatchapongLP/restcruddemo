package com.ryoma.restcruddemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
//    This method is NOT required for registering a datasource bean!!
//    Spring autoconfigures the dataSource bean with the provided information in the application.properties

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")  // Binds properties, required for manual bean configuration
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

//    This method is NOT required for registering the jdbcTemplate bean!?
//    No @Autowired is needed for injecting the dataSource bean too!?

//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}
