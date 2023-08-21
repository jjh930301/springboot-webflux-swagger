package com.webflux.example.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi groupApi() {
    return GroupedOpenApi.builder()
      .group("Webflux example")
      .pathsToMatch("/**")
      .build();
  }

  @Bean
  public OpenAPI openAPI() {
    String jwt = "JWT";
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt); // 헤더에 토큰 포함
    Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
      .name(jwt)
      .type(SecurityScheme.Type.HTTP)
      .scheme("bearer")
      .bearerFormat("JWT")
      .in(SecurityScheme.In.HEADER)
      .name(HttpHeaders.AUTHORIZATION)
    );
    return new OpenAPI()
      .info(new Info()
      .description("")
      .title("Webflux test"))
      .addSecurityItem(securityRequirement)
      .components(components);
  }

}
