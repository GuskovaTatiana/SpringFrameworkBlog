package ru.yandex.practicum;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.FileService;
import ru.yandex.practicum.service.PostService;

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
        return new FileService();
    }

    @Bean
    public PostService postService(CommentService commentService, FileService fileService, PostRepository postRepository, PostMapper postMapper) {
        return new PostService(commentService, fileService, postRepository, postMapper);
    }

    @Bean
    public CommentService commentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        return new CommentService(commentRepository, commentMapper);
    }
}
