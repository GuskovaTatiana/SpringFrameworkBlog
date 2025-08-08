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
    public List<CommentToPost> findAllByPostId(Integer postId) {
        String sql = """
                select id, content, created_at
                from t_comments_to_post
                where deleted = false
                  and post_id = %s;
                """;
        return jdbcTemplate.query(
                String.format(sql, postId),
                (rs, rowNum) -> {
                    return new CommentToPost(
                            rs.getInt("id"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at").toLocalDateTime());
                });
    }

    @Override
    public CommentToPost findById(Integer id) {
        CommentToPost comment = new CommentToPost();
        String sql = """
                select id, post_id, content, created_at
                from t_comments_to_post
                where deleted = false
                  and id = %s;
                """;
        jdbcTemplate.query(
                String.format(sql, id),
                rs -> {
                    comment.setId(rs.getInt("id"));
                    comment.setPostId(rs.getInt("post_id"));
                    comment.setContent(rs.getString("content"));
                    comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                });
        return comment;
    }


    @Override
    public Map<Integer, Integer> findCountCommentsByPostIds(List<Integer> postIds) {
        String sql = """
                select post_id, count(id) as count_comment
                from t_comments_to_post
                where deleted = false
                  and post_id in (%s)
                group by post_id;
                """;
        String joinedString = String.join(", ", postIds.stream().map(String::valueOf).toList());
        Map<Integer, Integer> result = new HashMap<>();
        jdbcTemplate.query(
                String.format(sql, joinedString),
                (rs, rowNum) -> {
                    Integer postId = rs.getInt("post_id");
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
    public void update(Integer id, String content) {
        String sql = """
                update t_comments_to_post
                set content = ?
                where id = ?;
                """;
        jdbcTemplate.update(sql,
                content,  id);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = """
                update t_comments_to_post
                set deleted = true
                where id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }
}
