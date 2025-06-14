package com.tekup.tp1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Management API")
                        .description("Comprehensive REST API for managing Users, Roles, Projects, and Comments with CRUD operations and advanced methods")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("fadi")
                                .email("fadi.mriri@tek-up.de")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:9090")
                                .description("Development Server")
                ));
    }
}
