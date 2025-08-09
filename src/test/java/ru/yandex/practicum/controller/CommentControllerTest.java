package ru.yandex.practicum.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.WebConfiguration;
import ru.yandex.practicum.config.DataSourceConfiguration;
import ru.yandex.practicum.config.ThymeleafConfiguration;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.testconfig.TestPostServiceConfig;
import ru.yandex.practicum.utils.TestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, WebConfiguration.class, ThymeleafConfiguration.class, TestPostServiceConfig.class})
@WebAppConfiguration
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private CommentService commentService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    //сохранение комментария
    @Test
    void createComment_shouldAddCommentToDatabaseAndRedirect() throws Exception {
        //моккаем сервис
        Mockito.doNothing().when(commentService).save(any(), any());
        mockMvc.perform(post("/posts/{postId}/comments", 1)
                        .param("commentText", "Комментарий 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    //обновление комментария
    @Test
    void updateComment_shouldEditCommentAndRedirect() throws Exception {
        //моккаем сервис
        Mockito.doNothing().when(commentService).update(any(), any());
        mockMvc.perform(post("/posts/{postId}/comments/{id}", 1, 1)
                        .param("commentText", "Обновление комментария 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    //удаление комментария
    @Test
    void deleteComment_shouldDeactivateCommentAndRedirect() throws Exception {
        //моккаем сервис
        Mockito.doNothing().when(commentService).delete(any());
        mockMvc.perform(post("/posts/{postId}/comments/{id}/delete", 1, 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

}
