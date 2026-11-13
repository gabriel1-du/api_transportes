package com.example.api_transporte.DTO.EmpresaTransporteDTO;

import org.springframework.stereotype.Component;

import com.example.api_transporte.Model.EmpresaTransporte;
import com.example.api_transporte.Model.Transportistas;

@Component 
public class TransportistaMapper {

    public getTransportistaDTO EntitytoGetDTO(Transportistas transport){

        getTransportistaDTO dto = new getTransportistaDTO();

        dto.setId_transportista(transport.getId_transportista());
        //Construimos el nombre completo
        dto.setNombres(transport.getP_nombre() + " " + transport.getS_nombre());
        //apellidos completos
        dto.setApellidos(transport.getP_apellido() + " "+ transport.getS_apellido());
        //rut completos
        dto.setRut_completo(transport.getCuerpo_rut() + "-" + transport.getDv_rut());

        return dto;


    }


    public getTransportistaDTO EntitytoSaveDTO(saveTransportista dto){

        
        Transportistas nuevoTranspor = new Transportistas();
        EmpresaTransporte empresa = new EmpresaTransporte();

        //Setteo de los atributos
        nuevoTranspor.setCuerpo_rut(dto.getCuerpo_rut());
        nuevoTranspor.setDv_rut(dto.getDv_rut());

        //guarmos la empresa para guardarlo en transportista
        empresa.setId_empresa_transporte(dto.getId_empresa_transporte());
        nuevoTranspor.setEmpresa_transporte(empresa);
        
        //nombres
        nuevoTranspor.setP_nombre(dto.getP_nombre());
        nuevoTranspor.setS_nombre(dto.getS_nombre());

        //apellidos
        nuevoTranspor.setP_apellido(dto.getP_apellido());
        nuevoTranspor.setS_apellido(dto.getS_apellido());

        //Nuevo objeto getDTO
        return this.EntitytoGetDTO(nuevoTranspor);

    }


    public Transportistas putDTOtoEntity(putTransportistaDTO dto){

        Transportistas transportista = new Transportistas();
        EmpresaTransporte empresa = new EmpresaTransporte();
        empresa.setId_empresa_transporte(dto.getId_empresa_transporte());

        //Setteo de los atributos
        transportista.setP_nombre(dto.getP_nombre());
        transportista.setS_nombre(dto.getS_nombre());
        transportista.setP_apellido(dto.getP_apellido());
        transportista.setS_apellido(dto.getS_apellido());
        transportista.setEmpresa_transporte(empresa);

        return transportista;

    }

}
