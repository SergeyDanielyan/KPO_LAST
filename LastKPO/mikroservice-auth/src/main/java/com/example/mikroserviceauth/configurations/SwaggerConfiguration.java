package com.example.mikroserviceauth.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {
    public OpenAPI api() {
        return new OpenAPI().servers(
                List.of(
                        new Server().url("http://localhost:8085")
                )
        ).info(
                new Info().title("Authorization API")
        );
    }
}
