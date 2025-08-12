package ru.yandex.practicum.web_blog.mapper;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.web_blog.model.dto.CreatePostDTO;
import ru.yandex.practicum.web_blog.model.dto.PostDTO;
import ru.yandex.practicum.web_blog.model.dto.SimplePostDTO;
import ru.yandex.practicum.web_blog.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PostMapper {

    public Post toEmpty(CreatePostDTO dto) {
        if (dto == null) {
            return null;
        }
        Post post = new Post();
        post.setTitle(dto.getTitle());
        String excerpt = dto.getContent() != null ? dto.getContent().split("\n")[0] : "";
        post.setExcerpt(excerpt.length() > 256 ? excerpt.substring(0, 255) : excerpt);
        post.setContent(dto.getContent());
        return post;
    }

    public Post toEmpty(Post post, CreatePostDTO dto) {
        if (dto == null) {
            return null;
        }
        post.setTitle(dto.getTitle() != null ? dto.getTitle() : post.getTitle());
        String excerpt = dto.getContent() != null ? dto.getContent() : post.getExcerpt();
        post.setExcerpt(excerpt.length() > 256 ? excerpt.substring(0, 255) : excerpt);
        post.setContent(dto.getContent() != null ? dto.getContent() : post.getContent());
        post.setUpdatedAt(LocalDateTime.now());
        return post;
    }

    public SimplePostDTO toSimpleDto(Post post, Integer commentCount) {
        return new SimplePostDTO(post.getId(),post.getTitle(), post.getImageUrl(), post.getExcerpt(), post.getLikeCount()
                ,commentCount, post.getTags(), post.getCreatedAt());

    }


    public PostDTO toDto(Post post) {
        return new PostDTO(post.getId(), post.getTitle(), post.getImageUrl(), post.getContent()
                ,post.getTags(), post.getLikeCount());
    }
    public List<SimplePostDTO> toSimpleDto(List<Post> posts, Map<Integer, Integer> mapCommentCount) {
        return posts.stream().map(it -> toSimpleDto(it, mapCommentCount.getOrDefault(it.getId(), 0))).toList();
    }

}
