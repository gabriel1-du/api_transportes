package com.example.api_transporte.DTO.PedidosDTO;

import lombok.Data;

@Data 
public class getPedidosDTO {

    //Id
    private Long id_pedido;
    private Long id_usuario;
    private String nombres;
    private String apellidos;
    private String rut_completo;
    private String fecha_envio;
    private String fecha_entregado;
    private boolean entregado;

}
