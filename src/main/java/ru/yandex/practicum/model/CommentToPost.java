package ru.yandex.practicum.model;

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
public class CommentToPost {
    private Integer id; // идентификатор комментария
    private Integer postId; // идентификатор поста к которому относиться комментарий
    private String content; // текст комментария
    private Boolean deleted = false; // признак удаления комментария
    private LocalDateTime createdAt; // дата создания


    public CommentToPost(Integer id, String content, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    public CommentToPost(Integer postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
