package ru.yandex.practicum.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.CommentToPost;
import ru.yandex.practicum.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class JdbcCommentRepositoryImpl implements CommentRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CommentToPost> findAllByPostId(Long postId) {
        String sql = """
                select id, content, created_at
                from t_comments_to_post
                where deleted = false
                  and id = %s;
                """;
        return jdbcTemplate.query(
                String.format(sql, postId),
                (rs, rowNum) -> {
                    String tagsJson = rs.getString("tags");
                    return new CommentToPost(
                            rs.getLong("id"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at").toLocalDateTime());
                });
    }

    @Override
    public Map<Long, Integer> findCountCommentsByPostIds(List<Long> postIds) {
        String sql = """
                select post_id, count(id) as count_comment
                from t_comments_to_post
                where deleted = false
                  and id in (%s)
                group by post_id;
                """;
        String joinedString = String.join(", ", postIds.stream().map(String::valueOf).toList());
        Map<Long, Integer> result = new HashMap<>();
        jdbcTemplate.query(
                String.format(sql, joinedString),
                (rs, rowNum) -> {
                    Long postId = rs.getLong("post_id");
                    Integer countComment = rs.getInt("count_comment");
                    result.put(postId, countComment);
                    return null;
                });
        return result;
    }

    @Override
    public void save(CommentToPost comment) {
        // Формируем insert-запрос с параметрами
        jdbcTemplate.update("insert into t_comments_to_post(post_id, content) values(?, ?)",
                comment.getPostId(), comment.getContent());
    }

    @Override
    public void update(CommentToPost comment) {
        String sql = """
                update t_comments_to_post
                set content = ?
                where id = ?;
                """;
        jdbcTemplate.update(sql,
               comment.getContent(),  comment.getPostId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = """
                update t_comments_to_post
                set deleted = true
                where id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }
}
