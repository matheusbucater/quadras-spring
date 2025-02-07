package com.github.matheusbucater.quadras_smc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Documentation", version = "v1"))
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearer-token", "bearer"))
                .components(new Components()
                        .addSecuritySchemes("bearer-token", new SecurityScheme()
                                .name("bearer-token")
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .scheme("Bearer")));
    }
}
