package ru.yandex.practicum.mapper;

import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.CommentToPost;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.model.dto.CommentDTO;
import ru.yandex.practicum.model.dto.PostDTO;
import ru.yandex.practicum.model.dto.SimplePostDTO;

import java.util.List;
import java.util.Map;

@Service
public class CommentMapper {
    public CommentDTO toDto(CommentToPost empty) {
        return CommentDTO.builder()
                .id(empty.getId())
                .content(empty.getContent())
                .createdAt(empty.getCreatedAt()).build();


    }

    public List<CommentDTO> toDto(List<CommentToPost> listComment) {
        return listComment.stream().map(it -> toDto(it)).toList();
    }

}
