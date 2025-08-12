package ru.yandex.practicum.web_blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class CreatePostDTO {
    private String title;
    private MultipartFile image;
    private String content;
    private String tags;

}
