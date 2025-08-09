package ru.yandex.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.model.dto.CreatePostDTO;
import ru.yandex.practicum.model.dto.PostDTO;
import ru.yandex.practicum.model.dto.SimplePostDTO;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    /**
     * Получение списка постов
     * */
    @GetMapping
    public String listOfPosts(
            @RequestParam(defaultValue = "0") int page, // Номер страницы (начинается с 0)
            @RequestParam(defaultValue = "5") int size, // Размер страницы (количество элементов на странице),
            @RequestParam(defaultValue = "") String searchTag, // Строка поиска по тэгам
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SimplePostDTO> simplePosts = postService.getPosts(pageable, searchTag);
        model.addAttribute("posts", simplePosts.getContent());
        model.addAttribute("paging", simplePosts);
        model.addAttribute("search", searchTag);
        return "list-of-post"; // Открывает страницу со списком постов
    }

    /**
     * Получение поста по идентификатору
     * */
    @GetMapping("{id}")
    public String getPost(@PathVariable int id, Model model) {
        PostDTO post = postService.getPostById(id);
        model.addAttribute("post", post);

        return "post"; // Открывает страницу с содержимым поста
    }

    /**
     * Открытие страницы для добавления нового пользователя
     * */
    @GetMapping("/add")
    public String openPageAddPost(Model model) {
        model.addAttribute("post", null);
        return "add-post"; // открываем страницу по созданию поста
    }

    /**
     * Сохранение нового поста
     * */
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createPost(@ModelAttribute CreatePostDTO createRequest) {
        if (createRequest.getTitle() != null && createRequest.getContent() != null) {
            postService.save(createRequest);

        }
        return "redirect:/posts";
    }

    /**
     *  Открытие страницы для редактирования поста
     * */
    @GetMapping("{id}/edit")
    public String openPageEditPost(@PathVariable int id, Model model) {
        PostDTO post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "add-post"; // открываем страницу по созданию поста
    }

    /**
     * Обновление поста
     * */
    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updatePost(@PathVariable int id, @ModelAttribute CreatePostDTO updateRequest) {
        if (updateRequest.getTitle() != null) {
            postService.update(id, updateRequest);

        }
        return "redirect:/posts";
    }

    /**
     * Удаление поста
     * */
    @PostMapping("{id}/delete")
    public String deletePost(@PathVariable int id) {

        postService.deletePostById(id);
        return "redirect:/posts"; // открываем страницу по созданию поста
    }

    @PostMapping("{id}/like")
    public String likePost(@PathVariable int id,
                           @RequestParam(defaultValue = "true")  Boolean like) {

        postService.setLikePostById(id, like);
        return "redirect:/posts/{id}"; // открываем страницу по созданию поста
    }
}
