package com.example.api_transporte.Startup;

import java.time.Duration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class MicroserviceConnectionChecker implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MicroserviceConnectionChecker.class);

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(5);

    private final List<Microservice> microservices;

    public MicroserviceConnectionChecker( //los parametros deben gaurdar los valores del .env
            @Value("${usuarios.service.url}") String usuariosUrl,
            @Value("${inventario.service.url}") String inventarioUrl,
            @Value("${compras.service.url}") String comprasUrl){
            
        //Lista de apis que se estan conectando        
        this.microservices = List.of(
                new Microservice("Usuarios", usuariosUrl),
                new Microservice("Inventario", inventarioUrl),
                new Microservice("Compras", comprasUrl)
        );
    }

    /**
     * Nombre con el que se identifica el microservicio y su URL base.
     */
    private record Microservice(String name, String url) {
        private Microservice {
            url = url.trim();
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Verificando conexion con los microservicios externos...");
        microservices.forEach(this::checkConnection);
    }

    private void checkConnection(Microservice microservice) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        requestFactory.setReadTimeout(READ_TIMEOUT);

        RestClient client = RestClient.builder()
                .baseUrl(microservice.url())
                .requestFactory(requestFactory)
                .build();

        try {
            int statusCode = client.get()
                    .uri("/")
                    .exchange((request, response) -> response.getStatusCode().value());

            log.info("Se establecio conexion con el microservicio de {} ({}). Estado HTTP: {}",
                    microservice.name(), microservice.url(), statusCode);

        } catch (RestClientResponseException e) {
            // El servicio respondio, pero con un codigo no 2xx en la raiz (404, 401, etc.):
            // eso igual confirma que el microservicio esta arriba y accesible.
            log.info("Se establecio conexion con el microservicio de {} ({}). Estado HTTP: {} (servicio accesible)",
                    microservice.name(), microservice.url(), e.getStatusCode().value());

        } catch (Exception e) {
            log.error("No se pudo establecer conexion con el microservicio de {} ({}): {}",
                    microservice.name(), microservice.url(), e.getMessage());
        }
    }
}
