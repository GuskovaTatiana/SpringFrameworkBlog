package ru.yandex.practicum.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Y. Tyurin on 15.10.2018
 */
@RequiredArgsConstructor
@Component
public class TestUtils {

    private final JdbcTemplate jdbcTemplate;

    public void executeSQL(String resource)  {
        try {
            String sql = new String(Files.readAllBytes(Paths.get(getClass().getResource(resource).toURI())));
            jdbcTemplate.execute(sql);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
