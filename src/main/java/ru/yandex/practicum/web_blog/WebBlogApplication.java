package ru.yandex.practicum.web_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class WebBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebBlogApplication.class, args);
	}

}
