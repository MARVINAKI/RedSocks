package com.example.redsocks.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info header = new Info();
        header.title("RedSocks Api");
        header.version("1.0.0");
        header.description("RESTful API by using JSON");
        header.contact(new Contact().email("kosakbig@gmail.com").name("Ivanov Konstantin"));
        return new OpenAPI().info(header);
    }
}
