package ru.yandex.practicum.web_blog.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.web_blog.model.dto.CommentDTO;
import ru.yandex.practicum.web_blog.model.dto.PostDTO;
import ru.yandex.practicum.web_blog.model.dto.SimplePostDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public MultipartFile mockFile = new MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            "This is test content.".getBytes()
    );

    public TestUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void executeSQL(String resource)  {
        try {
            String sql = new String(Files.readAllBytes(Paths.get(getClass().getResource(resource).toURI())));
            jdbcTemplate.execute(sql);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<SimplePostDTO> getListPost() {
        List<SimplePostDTO> simplePostDTOS = new ArrayList<>();
        simplePostDTOS.add(getSimplePost(1, "Пост 1", "/images/d59bc77b-5918-40fc-a90d-6bfe470aa3d8.png", "текст поста 1", 0, 0, List.of("новыйпост", "приветмир")));
        simplePostDTOS.add(getSimplePost(2, "Пост 2", null, "текст поста 2", 3, 2, List.of("тэг", "приветмир")));
        simplePostDTOS.add(getSimplePost(3, "Пост 3", null, "текст поста 3", 1, 2, null));

        return new PageImpl<>(simplePostDTOS, PageRequest.of(0, 5), 100);
    }

    public SimplePostDTO getSimplePost(Integer id, String title, String imageUrl, String except, Integer likeCount, Integer commentCount, List<String> tags) {
        return SimplePostDTO.builder()
                .id(id)
                .title(title)
                .imageUrl(imageUrl)
                .excerpt(except)
                .likeCount(likeCount)
                .tags(tags)
                .commentCount(commentCount)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public PostDTO getPost() {
        return PostDTO.builder()
                .id(1)
                .title("Пост 1")
                .imageUrl("/images/d59bc77b-5918-40fc-a90d-6bfe470aa3d8.png")
                .content("текст поста 1\n и еще какой то текст")
                .likeCount(0)
                .tags(List.of("новыйпост", "приветмир"))
                .comments(getListComment())
                .build();
    }

    public CommentDTO getComment() {
        return new CommentDTO(1, "Комментарий 1", LocalDateTime.now());
    }

    public List<CommentDTO> getListComment() {
        List<CommentDTO> comments = new ArrayList<>();
        comments.add(new CommentDTO(1, "Комментарий 1", LocalDateTime.now()));
        comments.add(new CommentDTO(2, "Комментарий 2", LocalDateTime.now()));
        return comments;
    }
}
