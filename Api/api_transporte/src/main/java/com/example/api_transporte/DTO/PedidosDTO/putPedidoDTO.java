package com.example.api_transporte.DTO.PedidosDTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data 
public class putPedidoDTO {

    private boolean estado_entrega;
    private LocalDateTime fecha_entregado;
}

