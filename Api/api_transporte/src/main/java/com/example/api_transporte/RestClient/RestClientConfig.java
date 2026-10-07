package com.example.api_transporte.RestClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration 
public class RestClientConfig {

    @Bean
    public RestClient historietaRestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8083/api/historietaRequest") // Endpoint base de usuarios
                .build();
    }

    @Bean
    public RestClient boletasRestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8084/api/boletas") // Endpoint base de usuarios
                .build();
    }

}
