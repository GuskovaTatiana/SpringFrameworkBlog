package ru.yandex.practicum.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
@AllArgsConstructor
public class PostsUtils {
    private final ObjectMapper objectMapper;

    /**
     * Преобразование списка в json
     * */
    public String convertListToJson(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        try {
            // Не создаем JSON-объект, а напрямую сериализуем List<String> в JSON-массив
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            // Обработка ошибок при сериализации (логирование, выброс исключения, ...)
            log.warn("Error converting list to JSON: " + e.getMessage());
            return null; // Или выбросить исключение
        }
    }

    /**
     * Преобразование json в список
     * */
    public List<String> convertJsonToList(String json) {
        List<String> list = new ArrayList<>();
        try {
            if (json != null) { // Проверяем, что JSON не nul
                list = objectMapper.readValue(json, List.class);
            }
        } catch (Exception e) {
            // Обработка ошибок при парсинге JSON (логирование, выброс исключения, ...)
            log.warn("Error parsing JSON: " + e.getMessage());

        }
        return list;
    }
}
