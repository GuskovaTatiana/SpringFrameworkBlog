package ru.yandex.practicum.web_blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.web_blog.utils.TestUtils;

@Configuration
public class TestConfig {
    @Bean
    public TestUtils testUtils(JdbcTemplate jdbcTemplate) {
        return new TestUtils(jdbcTemplate);
    }
}
