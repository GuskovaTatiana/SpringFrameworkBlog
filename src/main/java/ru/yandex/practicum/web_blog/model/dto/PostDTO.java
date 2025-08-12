package ru.yandex.practicum.web_blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Integer id;
    private String title;
    private String imageUrl;
    private String content;
    private List<String> tags;
    private Integer likeCount;
    private List<CommentDTO> comments = new ArrayList<>();

}
