package ru.yandex.practicum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

/**
 * Сущность основной информации поста в блоге
 * */
@Setter
@Getter
@RequiredArgsConstructor
public class Post {
    private Long id; // идентификатор поста
    private String title; // название поста
    private String imageUrl; // ссылка на картинку
    private String excerpt; // короткое содержание поста
    private String content = null; // полный текст поста
    private Boolean deleted = false; // признак удаления поста
    private List<String> tags; // список тегов
    private Integer likeCount = 0; // количество лайков на пост
    private LocalDateTime createdAt; // дата создания
    private LocalDateTime updatedAt; // дата обновления

    public Post(long id, String title, String imageUrl, String excerpt, List<String> tags, int likeCount, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.excerpt = excerpt;
        this.tags = tags;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }

    public Post(long id, String title, String imageUrl, String excerpt, String content, List<String> tags, int likeCount) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.excerpt = excerpt;
        this.tags = tags;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }
}
