package ru.yandex.practicum.web_blog.controller;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.yandex.practicum.web_blog.service.CommentService;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @MockitoBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;

    //сохранение комментария
    @Test
    void createComment_shouldAddCommentToDatabaseAndRedirect() throws Exception {
        Mockito.doNothing().when(commentService).save(any(), any());
        mockMvc.perform(post("/posts/{postId}/comments", 1)
                        .param("commentText", "Комментарий 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    //обновление комментария
    @Test
    void updateComment_shouldEditCommentAndRedirect() throws Exception {
        Mockito.doNothing().when(commentService).update(any(), any());
        mockMvc.perform(post("/posts/{postId}/comments/{id}", 1, 1)
                        .param("commentText", "Обновление комментария 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    //удаление комментария
    @Test
    void deleteComment_shouldDeactivateCommentAndRedirect() throws Exception {
        Mockito.doNothing().when(commentService).delete(any());
        mockMvc.perform(post("/posts/{postId}/comments/{id}/delete", 1, 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

}
