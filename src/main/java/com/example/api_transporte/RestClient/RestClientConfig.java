package com.example.api_transporte.RestClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration 
public class RestClientConfig {

    @Bean
    public RestClient historietaRestClient(
            @Value("${inventario.service.url}") String inventarioUrl) {
        return RestClient.builder()
                .baseUrl(inventarioUrl) // Endpoint base de inventario
                .build();
    }

    @Bean
    public RestClient boletasRestClient(
            @Value("${compras.service.url}") String comprasUrl) {
        return RestClient.builder()
                .baseUrl(comprasUrl) // Endpoint base de boletas
                .build();
    }

    @Bean
    public RestClient usuariosRestClient(
            @Value("${usuarios.service.url}") String usuariosUrl) {
        return RestClient.builder()
                .baseUrl(usuariosUrl) // Endpoint real de la API de usuarios
                .build();
    }

}
