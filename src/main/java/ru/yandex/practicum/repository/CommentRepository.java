package ru.yandex.practicum.repository;

import ru.yandex.practicum.model.CommentToPost;
import ru.yandex.practicum.model.Post;

import java.util.List;
import java.util.Map;

public interface CommentRepository {
    List<CommentToPost> findAllByPostId(Integer postId);
    CommentToPost findById(Integer id);

    Map<Integer, Integer> findCountCommentsByPostIds(List<Integer> postIds);

    void save(CommentToPost comment);
    void update(Integer id, String content);

    void deleteById(Integer id);
}
