package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.model.CommentToPost;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.model.dto.CommentDTO;
import ru.yandex.practicum.model.dto.SimplePostDTO;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.repository.PostRepository;

import java.util.HashMap;
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
    public List<CommentDTO> findAllByPostId(Long postId) {
        List<CommentToPost> comments = commentRepository.findAllByPostId(postId);
        return commentMapper.toDto(comments);
    }

    public Map<Long, Integer> getCountCommentsByPostIds(List<Long> postIds) {
        return commentRepository.findCountCommentsByPostIds(postIds);
    }
    ;
}
