package ru.yandex.practicum.web_blog.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class SimplePostDTO {
    private Integer id;
    private String title;
    private String imageUrl;
    private String excerpt;
    private Integer likeCount;
    private Integer commentCount;
    private List<String> tags;
    private LocalDateTime createdAt;
}
