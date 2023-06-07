package com.config.swagger;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing
@EnableWebMvc
@Log4j2
@EntityScan(basePackages = {"com.config.swagger.entities"})
public class SwaggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
        log.info("\n\n\t\t\uD83D\uDE80 \uD83D\uDEF8 \uD83C\uDF0C \"{}\" started as service \"{}\", " +
                        "Position \"{}\" address \"{}\" \uD83C\uDF0C \uD83D\uDEF8 \uD83D\uDE80\n" + "",
                "Bangph.work@gmail.com", "Demo", "dev", "Hà Tĩnh");
    }

}
