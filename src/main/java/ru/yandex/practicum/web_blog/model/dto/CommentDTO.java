package ru.yandex.practicum.web_blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentDTO {
    private Integer id; // идентификатор комментария
    private String content; // текст комментария
    private LocalDateTime createdAt; // дата создания

}
