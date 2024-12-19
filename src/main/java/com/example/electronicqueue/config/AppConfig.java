package com.example.electronicqueue.config;

import com.example.electronicqueue.service.QueueService;
import com.example.electronicqueue.repository.QueueRepository;
import com.example.electronicqueue.model.Place;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("singleton")
    public QueueService singletonQueueService(QueueRepository queueRepository) {
        return new QueueService(queueRepository);
    }

    @Bean
    @Scope("prototype")
    public Place prototypePlace() {
        return new Place();
    }
}

