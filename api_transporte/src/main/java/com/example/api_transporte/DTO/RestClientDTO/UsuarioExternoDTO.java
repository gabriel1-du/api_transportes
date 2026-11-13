package com.example.api_transporte.DTO.RestClientDTO;

import lombok.Data;

@Data 
public class UsuarioExternoDTO {

    private Long id_usuario;
    private String p_nombre;
    private String s_nombre;
    private String p_apellido;
    private String s_apellido;
    private String correo_elec;
    private String dv_rut;
    private String rut;

}
