package com.LMS.LibraryManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(LibraryManagementSystemApplication.class, args);

	}


	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("books");
		cacheManager.setCaffeine(
				com.github.benmanes.caffeine.cache.
						Caffeine.newBuilder()
						.maximumSize(100)
						.expireAfterWrite(10, java.util.concurrent.TimeUnit.MINUTES)
		);
		return cacheManager;
	}

}
