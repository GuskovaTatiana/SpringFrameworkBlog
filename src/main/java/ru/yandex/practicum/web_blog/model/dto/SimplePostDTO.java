package ru.yandex.practicum.web_blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SimplePostDTO {
    private Integer id;
    private String title;
    private String imageUrl;
    private String excerpt;
    private Integer likeCount = 0;
    private Integer commentCount = 0;
    private List<String> tags;
    private LocalDateTime createdAt;

}
