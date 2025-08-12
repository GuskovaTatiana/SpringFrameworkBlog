package ru.yandex.practicum.web_blog.repository;

import ru.yandex.practicum.web_blog.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();

    List<Post> findByFilter(int pageSize, int pageNumber, String searchTag);

    long countTotalPost();

    Post findById(Integer id);

    void save(Post post);

    void update(Post post);

    void deleteById(Integer id); //Деактивация поста

    void setLikeById(Integer id, Integer likeCount); //Обновление поля количество лайков
}
