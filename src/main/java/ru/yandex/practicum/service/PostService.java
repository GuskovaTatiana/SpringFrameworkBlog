package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yandex.practicum.model.dto.CreatePostDTO;
import ru.yandex.practicum.model.dto.PostDTO;
import ru.yandex.practicum.model.dto.SimplePostDTO;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.repository.PostRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final CommentService commentService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final String uploadDirectory = "/images";
    private final String regexPattern = "[\\n\\s,./\\\\!@$%^&*()_+=\\-~{}\\[\\]:;\"'<>?|]+";


    /**
     * Получение полного списка постов
     * */
    public List<SimplePostDTO> findAll() {
        List<Post> posts = postRepository.findAll();
        return postMapper.toSimpleDto(posts, new HashMap<>());
    }


    /**
     * Получение списка постов c паджинацией
     * */
    public Page<SimplePostDTO> getPosts(Pageable pageable, String searchTag) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        long totalPage = postRepository.countTotalPost();
        List<Post> posts = postRepository.findByFilter(pageSize, pageNumber, searchTag);
        //todo получить мапинг комментариев по списку идентификаторов
        Map<Long, Integer> commentsCountMap = new HashMap<>();
        if (!posts.isEmpty()) {
            List<Long> postIds = posts.stream().map(Post::getId).toList();
            commentsCountMap = commentService.getCountCommentsByPostIds(postIds);
        }
        List<SimplePostDTO> dto = postMapper.toSimpleDto(posts, commentsCountMap);

        return new PageImpl<>(dto, pageable, totalPage);
    }

    /**
     * Сохранение поста
     * */
    public void save(CreatePostDTO dto) {
        Post newPost = postMapper.toEmpty(dto);
        // записываем на сервере изображение и сохраняем в пост его url
        if (dto.getImage() != null) {
            try{
                String urlFile = storeFile(dto.getImage());
                newPost.setImageUrl(urlFile);
            } catch (IOException ex) {
                log.warn(ex.getMessage());
            }
        }
        // обрабатываем список тэгов
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            String strTags = dto.getTags().replaceAll(regexPattern, "");
            List<String> tags = Arrays.stream(strTags.split("#")).toList();
            newPost.setTags(tags.stream().filter(it -> !it.isEmpty()).toList());
        }
        postRepository.save(newPost);
    }

    /**
     * обновление поста
     * */
    public void update(Integer id, CreatePostDTO dto) {
        Post post = postRepository.findById(id);
        post = postMapper.toEmpty(post, dto);
        // записываем на сервере изображение и сохраняем в пост его url
        if (dto.getImage() != null) {
            try{
                //todo реализовать удаление старого файла
                String urlFile = storeFile(dto.getImage());
                post.setImageUrl(urlFile);
            } catch (IOException ex) {
                log.warn(ex.getMessage());
            }
        }
        // обрабатываем список тэгов
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            String strTags = dto.getTags().replaceAll(regexPattern, "");
            List<String> tags = Arrays.stream(strTags.split("#")).toList();
            post.setTags(tags.stream().filter(it -> !it.isEmpty()).toList());
        }
        postRepository.update(post);
    }

    /**
     * Получение полной информации по посту
     * */
    public PostDTO getPostById(Integer id) {
        Post post = postRepository.findById(id);
        PostDTO dto = postMapper.toDto(post);
        dto.setComments(commentService.findAllByPostId(post.getId()));
        return dto;
    }

    /**
     * Удаление поста
     * */
    public void deletePostById(Integer id) {
        postRepository.deleteById(id);
    }

    /**
     * Поставить лайк/биздайк посту
     * */
    public PostDTO setLikePostById(Integer id, boolean isLike) {
        Post post = postRepository.findById(id);
        if (isLike) {
            post.setLikeCount(post.getLikeCount() + 1);
        } else {
            post.setLikeCount(post.getLikeCount() - 1);
        }
        postRepository.setLikeById(id, post.getLikeCount());
        PostDTO dto = postMapper.toDto(post);
        dto.setComments(commentService.findAllByPostId(post.getId()));
        return dto;
    }


    /**
     * Сохраняет файл и возвращает URL для доступа к нему.
     * @param file  Файл для сохранения
     * @return URL сохраненного файла
     * @throws IOException  Если произошла ошибка при сохранении файла
     */
    public String storeFile(MultipartFile file) throws IOException {
        String filename = generateUniqueFilename(file.getOriginalFilename());
        //String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().replacePath(null).build().toUriString();
        String baseUrl = "/Users/terohovets/Desktop/MAIN/web-blog";
        Path uploadPath = Paths.get(baseUrl + uploadDirectory);

        // Создаем папку, если она не существует
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = Paths.get(baseUrl + uploadDirectory, filename);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to store file " + filename, e);
        }

        return uploadDirectory + "/" + filename;
    }

    /**
     * Генерирует уникальное имя файла.
     * @param originalFilename  Исходное имя файла
     * @return Уникальное имя файла
     */
    private String generateUniqueFilename(String originalFilename) {
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }
        return UUID.randomUUID().toString() + extension;
    }

}
