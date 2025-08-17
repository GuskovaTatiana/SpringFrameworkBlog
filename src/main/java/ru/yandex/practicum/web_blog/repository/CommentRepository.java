package ru.yandex.practicum.web_blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.web_blog.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "SELECT c FROM Comment c WHERE c.deleted = false AND c.postId = :postId")
    List<Comment> findAllByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query(value = "update Comment set deleted = true where id = :id")
    void setDeactivateById(Integer id); //Обновление поля активности поста
}
