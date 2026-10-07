package com.example.api_transporte.RestClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.api_transporte.DTO.RestClientDTO.UsuarioExternoDTO;

@Component 
public class UsuarioClient {

    @Autowired
    private RestClient usuariosRestClient;

    public UsuarioExternoDTO getUsuarioById(Long id_usuario) {
        try {
            return usuariosRestClient.get()
                    .uri("/{id_usuario}", id_usuario)
                    .retrieve()
                    .body(UsuarioExternoDTO.class);
        } catch (Exception e) {
            // Si el servicio no responde o el usuario no existe
            return null;
        }
    }
}
