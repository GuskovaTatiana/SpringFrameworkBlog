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
    private Integer id;
    private String title;
    private String imageUrl;
    private String excerpt;
    private String content = null;
    private Boolean deleted = false;
    private List<String> tags;
    private Integer likeCount = 0;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(int id, String title, String imageUrl, String excerpt, List<String> tags, int likeCount, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.excerpt = excerpt;
        this.tags = tags;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }
}
