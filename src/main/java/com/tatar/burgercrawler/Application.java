package com.tatar.burgercrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class Application {

    @Bean(name = "taskExecutor")
    public TaskExecutor workExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setThreadNamePrefix("AsyncHtmlParser-");
        threadPoolTaskExecutor.setCorePoolSize(15);
        threadPoolTaskExecutor.setMaxPoolSize(15);
        threadPoolTaskExecutor.setQueueCapacity(3000);
        threadPoolTaskExecutor.afterPropertiesSet();

        return threadPoolTaskExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
