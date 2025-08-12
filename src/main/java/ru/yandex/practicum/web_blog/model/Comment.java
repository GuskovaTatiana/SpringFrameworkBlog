package ru.yandex.practicum.web_blog.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Сущность комментария к посту в блоге
 * */
@Setter
@Getter
@RequiredArgsConstructor
public class Comment {
    private Integer id;
    private Integer postId;
    private String content;
    private Boolean deleted = false;
    private LocalDateTime createdAt;


    public Comment(Integer id, String content, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comment(Integer postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
