package com.br.stockpro.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SwaggerStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String swaggerUrl = "http://localhost:8080/swagger-ui/index.html";
        System.out.println("\n\n🔗 Swagger disponível em: " + swaggerUrl + "\n");
    }
}