package ru.yandex.practicum.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class CreatePostDTO {
    private String title; // название поста
    private MultipartFile image; // путь к картинке
    private String content; // короткое содержание поста

    private String tags; // короткое содержание поста


    public CreatePostDTO(String title, MultipartFile image, String content, String tags) {
        this.title = title;
        this.image = image;
        this.content = content;
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public MultipartFile getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getTags() {
        return tags;
    }
}
