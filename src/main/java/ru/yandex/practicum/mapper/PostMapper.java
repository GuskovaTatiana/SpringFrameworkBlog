package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.dto.CreatePostDTO;
import ru.yandex.practicum.model.dto.PostDTO;
import ru.yandex.practicum.model.dto.SimplePostDTO;
import ru.yandex.practicum.model.Post;

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
        return SimplePostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .imageUrl(post.getImageUrl())
                .excerpt(post.getExcerpt())
                .likeCount(post.getLikeCount())
                .tags(post.getTags())
                .commentCount(commentCount)
                .createdAt(post.getCreatedAt())
                .build();

    }


    public PostDTO toDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .imageUrl(post.getImageUrl())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .tags(post.getTags())
                .build();
    }
    public List<SimplePostDTO> toSimpleDto(List<Post> posts, Map<Integer, Integer> mapCommentCount) {
        return posts.stream().map(it -> toSimpleDto(it, mapCommentCount.getOrDefault(it.getId(), 0))).toList();
    }

}
