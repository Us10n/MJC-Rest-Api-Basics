package by.us10n.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "by.us10n.repository")
public class TestConfig {

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig("/test.properties");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Profile("test")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
