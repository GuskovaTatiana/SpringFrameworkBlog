package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.model.dto.CommentDTO;
import ru.yandex.practicum.repository.CommentRepository;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
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
        Comment comments = commentRepository.findById(id);
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

        commentRepository.update(id, commentText);
    }

    /**
     * Удаление комментария
     * */
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }

    /**
     * Получение карты количество комментариев по посту
     */
    public Map<Integer, Integer> getCountCommentsByPostIds(List<Integer> postIds) {
        return commentRepository.findCountCommentsByPostIds(postIds);
    }
    ;
}
