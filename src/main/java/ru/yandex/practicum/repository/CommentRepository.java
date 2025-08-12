package ru.yandex.practicum.repository;

import ru.yandex.practicum.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentRepository {
    List<Comment> findAllByPostId(Integer postId);
    Comment findById(Integer id);

    Map<Integer, Integer> findCountCommentsByPostIds(List<Integer> postIds);

    void save(Comment comment);
    void update(Integer id, String content);

    void deleteById(Integer id);
}
