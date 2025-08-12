package ru.yandex.practicum.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.yandex.practicum.WebConfiguration;
import ru.yandex.practicum.config.DataSourceConfiguration;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.model.dto.CommentDTO;
import ru.yandex.practicum.utils.TestUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:config/application-it.properties")
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TestUtils testUtils;

    @BeforeEach
    void setUp() {
        // Добавление тестовых данных
        testUtils.executeSQL("/sql/insert_data_to_bd.sql");
    }
    @AfterEach
    void setDown() {
        // Добавление тестовых данных
        testUtils.executeSQL("/sql/clear_data_to_bd.sql");
    }

    // получение всех комментариев по посту
    @Test
    void findAll_shouldReturnListComment() {
        Integer postId = 1;
        List<CommentDTO> allComments = commentService.findAllByPostId(postId);

        assertNotNull(allComments);
        assertEquals(2, allComments.size());

        CommentDTO dto = allComments.get(0);
        assertEquals(1, dto.getId());
        assertNotNull(dto.getContent());
    }
    //Сохранение комментария

    @Test
    void createComment_shouldCreateCommentToPost() {
        Integer postId = 1;
        String commentText = "Тестовый текст для комментария";
        List<CommentDTO> oldAllComments = commentService.findAllByPostId(postId);
        commentService.save(postId, commentText);
        List<CommentDTO> newAllComments = commentService.findAllByPostId(postId);
        assertNotNull(newAllComments);
        assertEquals(1, newAllComments.size() - oldAllComments.size());
        assertEquals(true, newAllComments.stream().map(CommentDTO::getContent).toList().contains(commentText));

    }
    //Обновление комментария
    @Test
    void updateComment_shouldUpdateComment() throws IOException {
        Integer postId = 1;
        String commentText = "Обновление комментария";
        List<CommentDTO> oldAllComments = commentService.findAllByPostId(postId);
        CommentDTO updateDto = oldAllComments.get(0);
        commentService.update(updateDto.getId(), commentText);
        Comment dto = commentService.findById(updateDto.getId());
        assertNotNull(dto);
        assertNotEquals(dto.getContent(), updateDto.getContent());
        assertEquals(commentText, dto.getContent());
        assertEquals(dto.getId(), updateDto.getId());
        assertEquals(dto.getPostId(), postId);
    }

    //Удаление комментария
    @Test
    void deleteCommentById_shouldDeactivateComment() {
        Integer postId = 1;
        List<CommentDTO> oldAllComments = commentService.findAllByPostId(postId);
        CommentDTO deletedDto = oldAllComments.get(0);
        commentService.delete(deletedDto.getId());
        List<CommentDTO> newAllComments = commentService.findAllByPostId(postId);
        assertEquals(1, oldAllComments.size() - newAllComments.size());
        for (CommentDTO dto : newAllComments) {
            assertNotEquals(deletedDto, dto.getId());
        }
    }
}

