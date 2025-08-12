package ru.yandex.practicum.web_blog.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.yandex.practicum.web_blog.service.CommentService;

@Controller
@AllArgsConstructor
@RequestMapping("/posts/{postId}")
public class CommentController {

    private final CommentService commentService;

    /**
     * Сохранение комментария к посту
     * */
    @PostMapping("/comments")
    public String createComment(@PathVariable int postId,
                                @RequestParam String commentText) {
        if (commentText != null) {
             commentService.save(postId, commentText);
        }
        return "redirect:/posts/{postId}";
    }

    /**
     * Редактирование комментария к посту
     * */
    @PostMapping("/comments/{id}")
    public String updateComment(@PathVariable int postId,
                                @PathVariable int id,
                                @RequestParam String commentText) {
        if (commentText != null) {
            commentService.update(id, commentText);
        }
        return "redirect:/posts/{postId}";
    }

    /**
     * Удаление комментария к посту
     * */
    @PostMapping("/comments/{id}/delete")
    public String deleteComment(@PathVariable int postId,
                                @PathVariable int id) {
            commentService.delete(id);
        return "redirect:/posts/{postId}";
    }
}
