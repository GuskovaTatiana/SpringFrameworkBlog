package ru.yandex.practicum.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PostDTO {
    private Long id; // идентификатор поста
    private String title; // название поста
    private String imageUrl; // ссылка на картинку
    private String content; // полный текст поста
    private List<String> tags; // список тегов
    private Integer likeCount; // количество лайков на пост
    private List<CommentDTO> comments;
}
