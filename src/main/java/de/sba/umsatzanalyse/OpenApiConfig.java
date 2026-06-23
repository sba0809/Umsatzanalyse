package de.sba.umsatzanalyse;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI umsatzanalyseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Umsatzanalyse API")
                        .description("Minimale Spring-Boot-Anwendung mit Swagger/OpenAPI")
                        .version("v1")
                        .contact(new Contact().name("SBA"))
                        .license(new License().name("Intern")));
    }
}