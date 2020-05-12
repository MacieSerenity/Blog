package com.lgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com")
@ComponentScan("com.lgr.web.admin")
@ComponentScan("com.lgr.web")
@EnableJpaRepositories("com.lgr.dao")
@EntityScan(basePackages = {"com.lgr.po"})
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
