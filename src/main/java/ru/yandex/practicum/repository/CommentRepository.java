package ru.yandex.practicum.repository;

import ru.yandex.practicum.model.CommentToPost;
import ru.yandex.practicum.model.Post;

import java.util.List;
import java.util.Map;

public interface CommentRepository {
    List<CommentToPost> findAllByPostId(Long postId);

    Map<Long, Integer> findCountCommentsByPostIds(List<Long> postIds);

    void save(CommentToPost comment);
    void update(CommentToPost comment);

    void deleteById(Long id);
}
