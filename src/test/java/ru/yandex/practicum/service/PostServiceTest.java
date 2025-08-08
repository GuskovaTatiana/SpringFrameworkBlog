package ru.yandex.practicum.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.WebConfiguration;
import ru.yandex.practicum.config.DataSourceConfiguration;
import ru.yandex.practicum.model.dto.CreatePostDTO;
import ru.yandex.practicum.model.dto.PostDTO;
import ru.yandex.practicum.model.dto.SimplePostDTO;
import ru.yandex.practicum.testconfig.TestFileServiceConfig;
import ru.yandex.practicum.utils.TestUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, WebConfiguration.class, TestFileServiceConfig.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:config/application-it.properties")
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private  FileService fileService;

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

    @Test
    void findAll_shouldReturnListSimplePosts() {
        List<SimplePostDTO> allPosts = postService.findAll();

        assertNotNull(allPosts);
        assertEquals(3, allPosts.size());

        SimplePostDTO post = allPosts.get(1);
        assertEquals(2, post.getId());
        assertEquals("Пост 2", post.getTitle());
        assertEquals(1, post.getCommentCount());
    }
    // получение списка постов
    @Test
    void getPosts_shouldReturnListSimplePostsWithoutSearchTagText() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<SimplePostDTO> posts = postService.getPosts(pageable, "");

        assertNotNull(posts.getContent());
        assertEquals(3, posts.getContent().size());

        SimplePostDTO post = posts.getContent().get(0);
        assertEquals(1, post.getId());
        assertEquals("Пост 1", post.getTitle());
        assertEquals(2, post.getCommentCount());
    }

    @Test
    void getPosts_shouldReturnEmptyListWithSearchTagText() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<SimplePostDTO> posts = postService.getPosts(pageable, "тэготсутсвует");

        assertEquals(0, posts.getContent().size());
    }

    // получение поста по идентификатору
    @Test
    void getPostById_shouldReturnPost() {
        PostDTO post = postService.getPostById(1);

        assertNotNull(post);
        assertEquals(1, post.getId());
        assertEquals("Пост 1", post.getTitle());
        assertEquals(2, post.getTags().size());
        assertEquals(2, post.getComments().size());
    }
    // удаление поста
    @Test
    void deletePostById_shouldDeactivatePost() {
        Integer deletedPostId = 1;
        List<SimplePostDTO> allPosts = postService.findAll();
        postService.deletePostById(deletedPostId);
        List<SimplePostDTO> allPostsAfterDelete = postService.findAll();
        assertEquals(1, allPosts.size() - allPostsAfterDelete.size());
        for (SimplePostDTO dto : allPostsAfterDelete) {
            assertNotEquals(deletedPostId, dto.getId());
        }
    }

    // поставить лайк посту
    @Test
    void setLikePostById_shouldIncrementLikeToPost() {
        Integer postId = 1;
        PostDTO oldPost = postService.getPostById(postId);
        postService.setLikePostById(postId, true);
        PostDTO oneLikePost = postService.getPostById(postId);
        postService.setLikePostById(postId, true);
        PostDTO twoLikePost = postService.getPostById(postId);
        postService.setLikePostById(postId, false);
        PostDTO oneDisLikePost = postService.getPostById(postId);
        assertEquals(1, oneLikePost.getLikeCount() - oldPost.getLikeCount());
        assertEquals(2, twoLikePost.getLikeCount() - oldPost.getLikeCount());
        assertEquals(1, oneDisLikePost.getLikeCount() - oldPost.getLikeCount());

    }

    // создание поста
    @Test
    void createPost_shouldCreatePostWithoutImage() {
        String postTitle = "Новый пост";
        CreatePostDTO createPostDTO = new CreatePostDTO(postTitle, null, "Какой то длинющий текст с символами \\n и дополнительным текстом", "#тэг, #новый");
        postService.save(createPostDTO);
        List<SimplePostDTO> allPosts = postService.findAll();
        List<Integer> postIds = allPosts.stream().filter(it -> it.getTitle().equalsIgnoreCase(createPostDTO.getTitle())).map(SimplePostDTO::getId).toList();
        assertNotNull(postIds);
        PostDTO firstCreatedPost = postService.getPostById(postIds.get(0));
        assertEquals(createPostDTO.getTitle(), firstCreatedPost.getTitle());
        assertEquals(createPostDTO.getContent(), firstCreatedPost.getContent());
        assertEquals(2, firstCreatedPost.getTags().size());
        assertNull(firstCreatedPost.getImageUrl());
    }

    @Test
    void createPost_shouldCreatePostWithImage() throws IOException {
        String postTitle = "Новый пост";
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is test content.".getBytes()
        );
        Mockito.when(fileService.storeFile(any())).thenReturn("/images/test.png");
        CreatePostDTO createPostDTO = new CreatePostDTO(postTitle, mockFile, "Какой то длинющий текст с символами \n и дополнительным текстом", "#тэг, #новый");
        postService.save(createPostDTO);
        List<SimplePostDTO> allPosts = postService.findAll();
        List<Integer> postIds = allPosts.stream().filter(it -> it.getTitle().equalsIgnoreCase(createPostDTO.getTitle())).map(SimplePostDTO::getId).toList();
        assertNotNull(postIds);
        PostDTO firstCreatedPost = postService.getPostById(postIds.get(0));
        assertEquals(createPostDTO.getTitle(), firstCreatedPost.getTitle());
        assertEquals(createPostDTO.getContent(), firstCreatedPost.getContent());
        assertEquals(2, firstCreatedPost.getTags().size());
        assertNotNull(firstCreatedPost.getImageUrl());
    }
    // обновление поста
    @Test
    void updatePost_shouldUpdatePostWithImage() throws IOException {
        Integer postId = 1;
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is test content.".getBytes()
        );
        Mockito.doNothing().when(fileService).deleteFile(any(String.class));
        Mockito.when(fileService.storeFile(any())).thenReturn("/images/test.png");
        CreatePostDTO createPostDTO = new CreatePostDTO("Обновление поста с id 1", mockFile, "Какой то длинющий текст с символами \n и дополнительным текстом", null);
        PostDTO oldPost = postService.getPostById(postId);
        postService.update(postId, createPostDTO);
        PostDTO newPost = postService.getPostById(postId);
        assertNotNull(newPost);
        assertNotEquals(oldPost.getTitle(), newPost.getTitle());
        assertEquals(createPostDTO.getTitle(), newPost.getTitle());
        assertNotEquals(oldPost.getContent(), newPost.getContent());
        assertNotEquals(oldPost.getImageUrl(), newPost.getImageUrl());
        assertNotNull(newPost.getTags());
        assertEquals(oldPost.getTags().size(), newPost.getTags().size());
    }
    //todo сдлеать мок-костыль для файлов

}

