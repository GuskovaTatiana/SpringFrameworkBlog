package ru.yandex.practicum.web_blog.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostDTO {
    private Integer id;
    private String title;
    private String imageUrl;
    private String content;
    private List<String> tags;
    private Integer likeCount;
    private List<CommentDTO> comments;

}
