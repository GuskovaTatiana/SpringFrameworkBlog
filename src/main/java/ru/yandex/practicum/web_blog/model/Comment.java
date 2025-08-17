package ru.yandex.practicum.web_blog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Сущность комментария к посту в блоге
 * */
@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "t_comments_to_post")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_comments_to_post_id_seq")
    @SequenceGenerator(name = "t_comments_to_post_id_seq", sequenceName = "t_comments_to_post_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, insertable=false, updatable=false)
    private Post post;

    @Column(name = "content")
    private String content;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment(Integer postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
