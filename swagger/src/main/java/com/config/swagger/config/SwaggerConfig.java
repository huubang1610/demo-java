package com.config.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:8090"));
        return new OpenAPI()
                .servers(servers)
                .components(new Components())
                .info(new Info().title("API DEMO")
                        .description("BangPh Create swagger 3")
                        .version("1.0.0"));
    }

    //http://localhost:8090/swagger-ui/index.html#/
}
