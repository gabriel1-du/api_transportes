package com.example.api_transporte.DTO.PedidosDTO;

import lombok.Data;

@Data 
public class savePedidoDTO {

    private Long id_usuario;
    private Long id_boleta;
    private Long id_transportista;

    // Fecha de envio desglosada para facilitar el POST
    private Integer anio;
    private Integer mes;
    private Integer dia;
    private Integer hora;

}
