package com.tatar.burgercrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Bean(name = "taskExecutor")
    public TaskExecutor workExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setThreadNamePrefix("AsyncHtmlParser-");
        threadPoolTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() + 1);
        threadPoolTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() + 1);
        threadPoolTaskExecutor.setQueueCapacity(1200);
        threadPoolTaskExecutor.afterPropertiesSet();

        return threadPoolTaskExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
