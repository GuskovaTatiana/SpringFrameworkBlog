package ru.yandex.practicum.testconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.service.FileService;

import static org.mockito.Mockito.mock;

@Configuration
public class TestFileServiceConfig {


    @Bean
    public FileService fileService() {
        // Создаём мок для FileService
        return mock(FileService.class);
    }
}
