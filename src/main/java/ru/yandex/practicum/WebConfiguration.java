package ru.yandex.practicum;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.yandex.practicum.service.FileService;

@Configuration
@EnableWebMvc
@PropertySource("classpath:config/application.properties")
@ComponentScan(basePackages = "ru.yandex.practicum")
public class WebConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public FileService fileService() {
        return new FileService();}
}
