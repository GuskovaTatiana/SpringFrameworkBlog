package ru.yandex.practicum.web_blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.web_blog.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(nativeQuery = true,
            value = "select * "
                    + "from t_posts tp "
                    + "where deleted = false  "
                    + "   AND (:searchTag IS NULL OR tp.tags @> CAST(CONCAT('[\"', :searchTag, '\"]') AS JSONB))"
                    + "order by created_at desc  "
                    + "LIMIT :pageSize OFFSET :offset ")
    List<Post> findByFilter(@Param(value = "pageSize") int pageSize, @Param(value = "offset") int offset, @Param(value = "searchTag") String searchTag);

    @Query(value = "SELECT COUNT(*) FROM Post WHERE deleted = false")
    long countTotalPost();

    @Modifying
    @Transactional
    @Query(value = "update Post set likeCount = :likeCount where id = :id")
    void setLikeById(Integer id, Integer likeCount); //Обновление поля количество лайков

    @Modifying
    @Transactional
    @Query(value = "update Post set deleted = true where id = :id")
    void setDeactivateById(Integer id); //Обновление поля активности поста
}
