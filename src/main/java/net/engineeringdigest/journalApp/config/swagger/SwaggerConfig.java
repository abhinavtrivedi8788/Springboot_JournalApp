package net.engineeringdigest.journalApp.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Journal API")
                                .description("Journal API Description")
                )
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8080"),
                        new Server().url("http://localhost:8081")
                ))
                .tags(
                        Arrays.asList(
                                new Tag().name("Public API's"),
                                new Tag().name("Users API's"),
                                new Tag().name("Journal API's"),
                                new Tag().name("Admin API's"),
                                new Tag().name("Redis API's")

                        )
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth", new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .in(SecurityScheme.In.HEADER)
                                                .name("Authorization")
                                )

                );
    }
}
