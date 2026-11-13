package com.example.api_transporte.Service;

import java.util.List;

import com.example.api_transporte.Model.Transportistas;

public interface TransportistasService {

    //get
    List<Transportistas> getAllTransportistas();

    Transportistas getTransportistaByid(Long id_transportista);
    //fin gets

    Transportistas saveTransportista(Transportistas transportistaNuevo);

    Transportistas putTransportista(Long id_transportista, Transportistas transportista);

    void deleteTransportista(Long id_transportista);
}
