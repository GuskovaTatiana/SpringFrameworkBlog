package ru.yandex.practicum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;


import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    // Настройка DataSource — компонент, отвечающий за соединение с базой данных
    @Bean
    public DataSource dataSource(
            // Настройки соединения возьмём из Environment
            @Value(value = "${spring.datasource.url}") String url,
            @Value(value = "${spring.datasource.username}") String username,
            @Value(value = "${spring.datasource.password}") String password,
            @Value(value = "${spring.datasource.driverClassName}") String driverClassName
    ) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    // JdbcTemplate — компонент для выполнения запросов
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // После инициализации контекста выполняем наполнение схемы базы данных
    @EventListener
    public void populate(ContextRefreshedEvent event) {
        DataSource dataSource = event.getApplicationContext().getBean(DataSource.class);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("sql/schema.sql")); // Файл должен находиться в ресурсах
        populator.execute(dataSource);
    }

}
