
CREATE TABLE if not exists t_posts (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- идентификатор
    title VARCHAR(255) NOT NULL, -- название поста
    image_url VARCHAR(255), -- url на изображение
    excerpt VARCHAR(255) NOT NULL, --краткое содержание
    deleted BOOLEAN DEFAULT FALSE, --признак удаления поста
    content TEXT NOT NULL, -- текст поста
    tags JSONB, -- список тэгов к посту
    like_count BIGINT DEFAULT 0, -- количество лайков к посту
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE if not exists t_comments_to_post(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- идентификатор
    post_id BIGINT not null constraint fk_post_id references t_posts, -- идентификатор поста, к которому относится коммент
    content TEXT, -- текст комментария
    deleted BOOLEAN DEFAULT FALSE, --признак удаления комментария
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP -- дата создания
    );
