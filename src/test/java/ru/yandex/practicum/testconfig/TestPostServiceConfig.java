package ru.yandex.practicum.testconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.PostService;

import static org.mockito.Mockito.mock;

@Configuration
public class TestPostServiceConfig {
    @Bean
    public PostService postService() {
        return mock(PostService.class);
    }

    @Bean
    public CommentService commentService() {
        return mock(CommentService.class);
    }
}
