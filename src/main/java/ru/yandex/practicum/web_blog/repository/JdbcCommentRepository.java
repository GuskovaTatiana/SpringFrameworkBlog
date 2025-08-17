package ru.yandex.practicum.web_blog.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class JdbcCommentRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<Integer, Integer> findCountCommentsByPostIds(List<Integer> postIds) {
        String sql = """
                select post_id, count(id) as count_comment
                from t_comments_to_post
                where deleted = false
                  and post_id in (:postIds)
                group by post_id;
                """;

        Map<Integer, Integer> result = namedParameterJdbcTemplate.query(
                sql,
                Map.of("postIds", postIds),
                (rs, rowNum) -> {
                    Integer postId = rs.getInt("post_id");
                    Integer countComment = rs.getInt("count_comment");
                    return Map.entry(postId, countComment); // Use Map.entry for concise creation
                }
        ).stream().collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
        return result;
    }
}
