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
    private Long id; // идентификатор комментария
    private Long postId; // идентификатор поста к которому относиться комментарий
    private String content; // текст комментария
    private Boolean deleted = false; // признак удаления комментария
    private LocalDateTime createdAt; // дата создания


    public CommentToPost(long id, String content, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }
}
