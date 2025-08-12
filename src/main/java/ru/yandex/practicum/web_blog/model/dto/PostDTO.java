package ru.yandex.practicum.web_blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

public class PostDTO {
    private Integer id; // идентификатор поста
    private String title; // название поста
    private String imageUrl; // ссылка на картинку
    private String content; // полный текст поста
    private List<String> tags; // список тегов
    private Integer likeCount; // количество лайков на пост
    private List<CommentDTO> comments;

    public PostDTO(Integer id, String title, String imageUrl, String content, List<String> tags, Integer likeCount) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
        this.tags = tags;
        this.likeCount = likeCount;
    }

    public PostDTO(Integer id, String title, String imageUrl, String content, List<String> tags, Integer likeCount, List<CommentDTO> comments) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
        this.tags = tags;
        this.likeCount = likeCount;
        this.comments = comments;
    }
}
