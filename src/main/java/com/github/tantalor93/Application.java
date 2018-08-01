package com.github.tantalor93;

import com.github.tantalor93.dto.FeedbackToCreate;
import com.github.tantalor93.service.FeedbacksService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner mockData(final FeedbacksService service) {
        return (args) -> {
            service.save(
                    new FeedbackToCreate("Jack", "Jack@gmail.com", "it works")
            );
            service.save(
                    new FeedbackToCreate("Zuzicka", "Zuzicka@gmail.com", "this sucks, please make it better")
            );
            service.save(
                    new FeedbackToCreate("Petr","Novak@gmail.com", "it is alright")
            );
            service.save(
                    new FeedbackToCreate("Tom","Sova@gmail.com", "Could be better")
            );
            service.save(
                    new FeedbackToCreate("Jan","Suk@gmail.com", "should be better")
            );
        };
    }
}
