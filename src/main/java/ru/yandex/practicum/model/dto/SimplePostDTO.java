package ru.yandex.practicum.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SimplePostDTO {
    private Integer id; // идентификатор поста
    private String title; // название поста
    private String imageUrl; // путь к картинке
    private String excerpt; // короткое содержание поста
    private Integer likeCount = 0; // количество лайков на пост
    private Integer commentCount = 0; // количество комментариев
    private List<String> tags; // теги к посту
    private LocalDateTime createdAt; // дата создания
}
