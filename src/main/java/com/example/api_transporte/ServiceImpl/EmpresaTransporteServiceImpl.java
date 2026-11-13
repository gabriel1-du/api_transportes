package com.example.api_transporte.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_transporte.Model.EmpresaTransporte;
import com.example.api_transporte.Repository.EmpresaTransporteRepository;
import com.example.api_transporte.Service.EmpresaTransporteService;

@Service
public class EmpresaTransporteServiceImpl implements EmpresaTransporteService {

    // Inyeccion de repositorios y dependencias
    @Autowired
    private EmpresaTransporteRepository empresaTransporteRepository;

    // metodos GET
    @Override
    public List<EmpresaTransporte> getAllEmpresasTrans() {
        return empresaTransporteRepository.findAll();
    }

    @Override
    public EmpresaTransporte getEmpresaTransByid(Long id_empresa) {
        return empresaTransporteRepository.findById(id_empresa)
                .orElseThrow(() -> new RuntimeException("Empresa de transporte no encontrada con id: " + id_empresa));
    }
    // --- FIN GET

    // metodos POST
    @Override
    public EmpresaTransporte saveEmpresaTrans(EmpresaTransporte empresaNueva) {
        return empresaTransporteRepository.save(empresaNueva);
    }

    // metodos PUT
    @Override
    public EmpresaTransporte putEmpresaTransporte(Long id_empresa, EmpresaTransporte empresa) {
        EmpresaTransporte empresa_existente = empresaTransporteRepository.findById(id_empresa)
                .orElseThrow(() -> new RuntimeException("Empresa de transporte no encontrada con id: " + id_empresa));

        empresa_existente.setNombre_empresa(empresa.getNombre_empresa());

        return empresaTransporteRepository.save(empresa_existente);
    }

    // metodos DELETE
    @Override
    public void deleteEmpresaTrans(Long id_empresa) {
        EmpresaTransporte empresa_eliminada = empresaTransporteRepository.findById(id_empresa)
                .orElseThrow(() -> new RuntimeException("Empresa de transporte no encontrada con id: " + id_empresa));

        empresaTransporteRepository.delete(empresa_eliminada);
    }
}
