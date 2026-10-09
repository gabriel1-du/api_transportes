package com.example.api_transporte.DTO.PedidosDTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class putPedidoDTO {

    // Acepta "entregado" o "entregado_estado" en el JSON (booleano true/false o 1/0)
    @JsonAlias({"entregado", "entregado_estado"})
    private boolean estado_entrega;

    private LocalDateTime fecha_entregado;
}
