package com.example.finanzaspersonalesapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API - Sistema de Gestión de Finanzas Personales")
                .version("1.0")
                .description("API REST para gestionar ingresos, gastos, presupuestos y reportes financieros.")
                .contact(new Contact()
                    .name("Universidad de Cundinamarca")
                    .email("ingenieria@ucundinamarca.edu.co")));
    }
}
