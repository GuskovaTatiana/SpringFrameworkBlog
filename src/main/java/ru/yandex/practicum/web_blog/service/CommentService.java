package ru.yandex.practicum.web_blog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.web_blog.mapper.CommentMapper;
import ru.yandex.practicum.web_blog.model.Comment;
import ru.yandex.practicum.web_blog.model.dto.CommentDTO;
import ru.yandex.practicum.web_blog.repository.CommentRepository;
import ru.yandex.practicum.web_blog.repository.JdbcCommentRepository;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final JdbcCommentRepository jdbcCommentRepository;
    private final CommentMapper commentMapper;

    /**
     * Получение полного списка комментариев по посту
     * */
    public List<CommentDTO> findAllByPostId(Integer postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return commentMapper.toDto(comments);
    }

    /**
     * Получение комментария по id
     * */
    public Comment findById(Integer id) {
        Comment comments = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        return comments;
    }

    /**
     * Сохранение комментария
     * */
    public void save(Integer postId, String commentText) {
        Comment comments = new Comment(postId, commentText);
        commentRepository.save(comments);
    }

    /**
     * Обновление комментария
     * */
    public void update(Integer id, String commentText) {
        Comment comments = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        comments.setContent(commentText);
        commentRepository.save(comments);
    }

    /**
     * Удаление комментария
     * */
    public void delete(Integer id) {
        commentRepository.setDeactivateById(id);
    }

    /**
     * Получение карты количество комментариев по посту
     */
    public Map<Integer, Integer> getCountCommentsByPostIds(List<Integer> postIds) {
        return jdbcCommentRepository.findCountCommentsByPostIds(postIds);
    }
    ;
}
