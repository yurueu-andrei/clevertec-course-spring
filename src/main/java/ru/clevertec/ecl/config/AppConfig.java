package ru.clevertec.ecl.config;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Value("${datasource.driver}")
    private String driverClass;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.user}")
    private String user;
    @Value("${datasource.password}")
    private String password;
    @Value("${spring.liquibase.change-log}")
    private String migrationsLocation;

    @Bean
    public SpringLiquibase springLiquibase() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog(migrationsLocation);
        return springLiquibase;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
