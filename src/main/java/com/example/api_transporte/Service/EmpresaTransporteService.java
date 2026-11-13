package com.example.api_transporte.Service;

import java.util.List;

import com.example.api_transporte.Model.EmpresaTransporte;

public interface EmpresaTransporteService {

    //get
    List<EmpresaTransporte> getAllEmpresasTrans();

    EmpresaTransporte getEmpresaTransByid(Long id_empresa);
    //fin gets

    EmpresaTransporte saveEmpresaTrans(EmpresaTransporte empresaNueva);

    EmpresaTransporte putEmpresaTransporte(Long id_empresa, EmpresaTransporte empresa);

    void deleteEmpresaTrans(Long id_empresa);
}
