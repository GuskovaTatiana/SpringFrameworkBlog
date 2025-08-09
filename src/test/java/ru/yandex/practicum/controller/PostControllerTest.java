package ru.yandex.practicum.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.WebConfiguration;
import ru.yandex.practicum.config.DataSourceConfiguration;
import ru.yandex.practicum.config.ThymeleafConfiguration;
import ru.yandex.practicum.model.dto.CreatePostDTO;
import ru.yandex.practicum.model.dto.PostDTO;

import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.testconfig.TestPostServiceConfig;
import ru.yandex.practicum.utils.TestUtils;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, WebConfiguration.class, ThymeleafConfiguration.class, TestPostServiceConfig.class})
@WebAppConfiguration
public class PostControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private PostService postService;
    private MockMvc mockMvc;



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //запрос на получение списка постов
    @SneakyThrows
    @Test
    void getListOfPosts_shouldReturnHtmlWithPosts(){
        //генерируем тестовый вариант ответа
        //моккаем сервис
        Mockito.when(postService.getPosts(any(), any())).thenReturn(testUtils.getListPost());
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("list-of-post"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(xpath("//table/tr").nodeCount(5))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[3]/td[1]/h2").string("Пост 1"));
    }

    // запрос на получение поста по идентификатору
    @Test
    void getPostById_shouldReturnHtmlWithPostById() throws Exception {
        //генерируем тестовый вариант ответа
        PostDTO post = testUtils.getPost();
        //моккаем сервис
        Mockito.when(postService.getPostById(any())).thenReturn(post);
        mockMvc.perform(get("/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(xpath("//table/tr").nodeCount(8))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[2]/td[1]/h2").string(post.getTitle()))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[6]/td[1]/form/span").string(post.getComments().get(0).getContent()))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[7]/td[1]/form/span").string(post.getComments().get(1).getContent()));
    }

    //открытие страницы add-post для добавления нового поста
    @Test
    void openPageAddPost_shouldReturnHtmlPageAddPost() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("add-post"))
             //   .andExpect(model().attributeExists("post"))
                .andExpect(xpath("//table/tr").nodeCount(5))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[1]/td[1]/h3").string("Название"))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[1]/td[1]/textarea").nodeCount(1));
    }

    //открытие страницы add-post для редактирования поста
    @Test
    void openPageEditPost_shouldReturnHtmlPageAddPostWithData() throws Exception {
        //генерируем тестовый вариант ответа
        PostDTO post = testUtils.getPost();
        //моккаем сервис
        Mockito.when(postService.getPostById(any())).thenReturn(post);
        mockMvc.perform(get("/posts/{id}/edit", post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(xpath("//table/tr").nodeCount(5))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[1]/td[1]/h3").string("Название"))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[1]/td[1]/textarea").nodeCount(1))
                .andExpect(MockMvcResultMatchers.xpath("//table/tr[1]/td[1]/textarea").string(post.getTitle()));
    }

    //сохранение поста
    @Test
    void createPost_shouldAddPostToDatabaseAndRedirect() throws Exception {
        //моккаем сервис
        Mockito.doNothing().when(postService).save(any());
        mockMvc.perform(multipart("/posts/").file((MockMultipartFile) testUtils.mockFile)
                        .param("title", "Пост 2")
                        .param("content", "текст Поста 2")
                        .param("tags", "#тэг, #новый")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    //обновление поста
    @Test
    void updatePost_shouldEditPostAndRedirect() throws Exception {
        CreatePostDTO createPostDTO = new CreatePostDTO("Пост 2", testUtils.mockFile, "Какой то длинющий текст с символами \n и дополнительным текстом", "#тэг, #новый");
        //моккаем сервис
        Mockito.doNothing().when(postService).update(any(), any());
        mockMvc.perform(multipart("/posts/{id}", 1).file((MockMultipartFile) testUtils.mockFile)
                        .param("title", "Обновленный Пост 2")
                        .param("content", "текст Поста 2")
                        .param("tags", "#новый")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    //удаление поста
    @Test
    void deletePost_shouldDeactivatePostAndRedirect() throws Exception {
        //моккаем сервис
        Mockito.doNothing().when(postService).deletePostById(any());
        mockMvc.perform(post("/posts/{id}/delete", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    //поставить лайк посту
    @Test
    void likePost_shouldSetLikeToPostAndRedirect() throws Exception {
        //моккаем сервис
        Mockito.when(postService.setLikePostById(any(), any(Boolean.class))).thenReturn(testUtils.getPost());
        mockMvc.perform(post("/posts/{id}/like", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

}
