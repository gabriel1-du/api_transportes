package com.example.api_transporte.DTO.EmpresaTransporteDTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component 
@Data 
public class saveTransportista {


    private Long id_empresa_transporte;

    private String p_nombre;

   
    private String s_nombre;

    
    private String p_apellido;

    
    private String s_apellido;

 
    private String cuerpo_rut;

    private String dv_rut;

}
