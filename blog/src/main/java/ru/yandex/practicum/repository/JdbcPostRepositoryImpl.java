package ru.yandex.practicum.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.utils.PostsUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class JdbcPostRepositoryImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PostsUtils utils;

    /**
     * Получение списка постов
     * */
    @Override
    public List<Post> findAll() {
        String sql = "select id, title, image_url, excerpt, like_count, created_at from t_posts where deleted = false";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    String tagsJson = rs.getString("tags");
                    return new Post(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("image_url"),
                            rs.getString("excerpt"),
                            utils.convertJsonToList(tagsJson),
                            rs.getInt("like_count"),
                            rs.getTimestamp("created_at").toLocalDateTime());
                });
    }

    /**
     * Получение списка постов по указанным фильтрам
     * */
    @Override
    public List<Post> findByFilter(int pageSize, int pageNumber, String searchTag) {
        // Формируем SQL-запрос с LIMIT и OFFSET
        String searchSqlText = searchTag.isEmpty() ? "" : "and tp.tags @> '[\"" + searchTag +"\"]'::jsonb \n";
        String sql = "select id, title, image_url, excerpt, tp.tags, like_count, created_at \n" +
                "from t_posts tp\n" +
                "where deleted = false \n " + searchSqlText +
                "order by created_at desc \n" +
                "LIMIT " + pageSize + " OFFSET " + pageNumber * pageSize + ";";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    String tagsJson = rs.getString("tags");
                    return new Post(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("image_url"),
                            rs.getString("excerpt"),
                            utils.convertJsonToList(tagsJson),
                            rs.getInt("like_count"),
                            rs.getTimestamp("created_at").toLocalDateTime());
                });
    }

    /**
     * Получение общего количества постов (для пагинации)
     * */
    @Override
    public long countTotalPost() {
        String sql = "SELECT COUNT(*) FROM t_posts WHERE deleted = false";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    /**
     * Получение данных по посту
     * */
    @Override
    public Post findById(Integer id) {
        Post post = new Post();
        String sql = """
                select id, title, image_url, excerpt, content, like_count, tp.tags
                from t_posts tp
                where deleted = false
                and id = %s;
                """;
        jdbcTemplate.query(
                String.format(sql, id),
                rs -> {
                    String tagsJson = rs.getString("tags");
                    post.setId(rs.getLong("id"));
                    post.setTitle(rs.getString("title"));
                    post.setImageUrl(rs.getString("image_url"));
                    post.setExcerpt(rs.getString("excerpt"));
                    post.setContent(rs.getString("content"));
                    post.setTags(utils.convertJsonToList(tagsJson));
                    post.setLikeCount(rs.getInt("like_count"));
                });
        return post;
    }

    /**
     * Сохранение нового поста
     * */
    @Override
    public void save(Post post) {
        // Формируем insert-запрос с параметрами
        jdbcTemplate.update("insert into t_posts(title, image_url, tags, excerpt, content) values(?, ?, ?::jsonb, ?, ?)",
                post.getTitle(), post.getImageUrl(), utils.convertListToJson(post.getTags()), post.getExcerpt(), post.getContent());
    }

    /**
     * Обновление данных поста
     * */
    @Override
    public void update(Post post) {
        String sql = """
                update t_posts
                set title = ? ,
                    image_url = ?,
                    excerpt = ?,
                    content = ?,
                    tags = ? ::jsonb,
                    updated_at = now()
                where id = ?;
                """;
        jdbcTemplate.update(sql,
                post.getTitle(), post.getImageUrl(), post.getExcerpt(), post.getContent(), utils.convertListToJson(post.getTags()), post.getId());
    }
    /**
     * Деактивация поста
     * */
    @Override
    public void deleteById(Integer id) {
        String sql = """
                update t_posts
                set deleted = true
                where id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void setLikeById(Integer id, Integer likeCount) {
        String sql = """
                update t_posts
                set like_count = ?
                where id = ?;
                """;
        jdbcTemplate.update(sql, likeCount, id);
    }
}
