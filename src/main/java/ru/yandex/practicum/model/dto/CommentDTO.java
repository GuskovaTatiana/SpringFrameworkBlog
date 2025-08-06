package ru.yandex.practicum.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentDTO {
    private Long id; // идентификатор комментария
    private String content; // текст комментария
    private LocalDateTime createdAt; // дата создания

}
