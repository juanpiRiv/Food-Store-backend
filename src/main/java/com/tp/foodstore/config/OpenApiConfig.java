package com.tp.foodstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la documentación OpenAPI (Swagger).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI foodStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Food Store API")
                        .version("1.0")
                        .description("API REST de gestión de pedidos de comida: usuarios, categorías, productos y pedidos."));
    }
}
