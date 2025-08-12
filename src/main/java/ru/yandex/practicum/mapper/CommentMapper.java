package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.model.dto.CommentDTO;

import java.util.List;

@Service
public class CommentMapper {
    public CommentDTO toDto(Comment empty) {
        return CommentDTO.builder()
                .id(empty.getId())
                .content(empty.getContent())
                .createdAt(empty.getCreatedAt()).build();


    }

    public List<CommentDTO> toDto(List<Comment> listComment) {
        return listComment.stream().map(it -> toDto(it)).toList();
    }

}
