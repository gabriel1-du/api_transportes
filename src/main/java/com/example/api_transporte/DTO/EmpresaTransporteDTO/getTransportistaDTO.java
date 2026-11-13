package com.example.api_transporte.DTO.EmpresaTransporteDTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id_transportista", "nombres", "apellidos", "rut_completo"})
public class getTransportistaDTO {

    private Long id_transportista;
    private String rut_completo;
    private String nombres;
    private String apellidos;
    


}
