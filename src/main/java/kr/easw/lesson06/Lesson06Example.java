package kr.easw.lesson06;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Lesson06Example {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Lesson06Example.class)
                .registerShutdownHook(true)
                .run(args);
    }
}
