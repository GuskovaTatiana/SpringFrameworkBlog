package ru.yandex.practicum.web_blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value(value = "${images.baseUrl}") String imageBaseUrl;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") // URL, по которому будут доступны ресурсы
                .addResourceLocations("file:" + imageBaseUrl + "/images/"); // Путь к директории с ресурсами
    }
}
