package com.example.api_transporte.DTO.EmpresaTransporteDTO;

import com.example.api_transporte.Model.EmpresaTransporte;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data 
public class putTransportistaDTO {

 
    private Long id_empresa_transporte;
    private String p_nombre;
    private String s_nombre;
    private String p_apellido;
    private String s_apellido;


}
