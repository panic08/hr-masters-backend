package ru.panic.mainservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFoxConfiguration {
    @Bean
    public GroupedOpenApi mainV1Api() {
        return GroupedOpenApi.builder()
                .group("main-v1-api")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public OpenAPI mainV1OpenApi() {
        return new OpenAPI()
                .info(new Info().title("HR Masters API V1")
                        .description("API V1 for HR Masters")
                );
    }
}
