package com.config.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:8090"));
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .servers(servers)
                .components(new Components().addSecuritySchemes(securitySchemeName,
                new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
                        .scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title("API DEMO")
                        .description("BangPh Create swagger 3")
                        .version("1.0.0"))
                ;
    }

}
