
INSERT INTO t_posts (id, title, image_url, excerpt, content, tags) VALUES (1, 'Пост 1', '/images/d59bc77b-5918-40fc-a90d-6bfe470aa3d8.png', 'текст', 'тектс', '["новыйпост", "приветмир"]');
INSERT INTO t_posts (id, title, image_url, excerpt, content, tags) VALUES (2, 'Пост 2', '/images/d59bc77b-5918-40fc-a90d-6bfe470aa3d8.png', 'текст', 'текcт', null);
INSERT INTO t_posts (id, title, image_url, excerpt, content, tags) VALUES (3, 'Пост 3', null, 'текст', 'текcт', '["тэг"]');
ALTER SEQUENCE t_posts_id_seq RESTART WITH 4;
INSERT INTO t_comments_to_post (id, post_id, content) VALUES (1, 1, 'какой то коммент');
INSERT INTO t_comments_to_post (id, post_id, content) VALUES (2, 1, 'валпиыулкоп');
INSERT INTO t_comments_to_post (id, post_id, content) VALUES (3, 2, 'коммент');
ALTER SEQUENCE t_comments_to_post_id_seq RESTART WITH 4;

