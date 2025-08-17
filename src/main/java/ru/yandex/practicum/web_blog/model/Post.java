package ru.yandex.practicum.web_blog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сущность основной информации поста в блоге
 * */

@Entity
@Table(name = "t_posts")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_posts_id_seq")
    @SequenceGenerator(name = "t_posts_id_seq", sequenceName = "t_posts_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "excerpt", nullable = false)
    private String excerpt;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "deleted")
    private Boolean deleted = false;
    @Column(name = "tags", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> tags;
    @Column(name = "like_count")
    private Integer likeCount = 0;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

}
