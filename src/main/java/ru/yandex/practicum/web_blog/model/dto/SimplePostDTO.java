package ru.yandex.practicum.web_blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SimplePostDTO {
    private Integer id; // идентификатор поста
    private String title; // название поста
    private String imageUrl; // путь к картинке
    private String excerpt; // короткое содержание поста
    private Integer likeCount = 0; // количество лайков на пост
    private Integer commentCount = 0; // количество комментариев
    private List<String> tags; // теги к посту
    private LocalDateTime createdAt; // дата создания

    public SimplePostDTO(Integer id, String title, String imageUrl, String excerpt, Integer likeCount, List<String> tags, Integer commentCount, LocalDateTime createdAt) {
    }

    public SimplePostDTO(Integer id, String title, String imageUrl, String excerpt, Integer likeCount, Integer commentCount, List<String> tags, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.excerpt = excerpt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.tags = tags;
        this.createdAt = createdAt;
    }
}
