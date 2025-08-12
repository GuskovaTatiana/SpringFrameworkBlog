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
    private Integer id;
    private String content;
    private LocalDateTime createdAt;

}
