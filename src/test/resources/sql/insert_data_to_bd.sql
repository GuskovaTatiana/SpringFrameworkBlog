insert into public.t_posts ( id, title, image_url, excerpt, content, tags, deleted, like_count, created_at, updated_at)
VALUES  (1, 'Пост 1', '/images/d59bc77b-5918-40fc-a90d-6bfe470aa3d8.png', 'текст', 'тектс', '["новыйпост", "приветмир"]', false, 0, now(), now()),
        (2, 'Пост 2', '/images/d59bc77b-5918-40fc-a90d-6bfe470aa3d8.png', 'текст', 'текcт', null, false, 0, now(), now()),
        (3, 'Пост 3', null, 'текст', 'текcт', '["тэг"]', false, 0, now(), now());
ALTER SEQUENCE t_posts_id_seq RESTART WITH 4;
INSERT INTO t_comments_to_post (id, post_id, content, deleted, created_at)
VALUES
    (1, 1, 'какой то коммент', false, now()),
    (2, 1, 'валпиыулкоп', false, now()),
    (3, 2, 'коммент', false, now());
ALTER SEQUENCE t_comments_to_post_id_seq RESTART WITH 4;

