package com.example.api_transporte.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_transporte.Model.EmpresaTransporte;
import com.example.api_transporte.Model.Transportistas;
import com.example.api_transporte.Repository.EmpresaTransporteRepository;
import com.example.api_transporte.Repository.TransportistaRepository;
import com.example.api_transporte.Service.TransportistasService;

@Service
public class TransportistasServiceImpl implements TransportistasService {

    // Inyeccion de repositorios y dependencias
    @Autowired
    private TransportistaRepository transportistaRepository;

    @Autowired
    private EmpresaTransporteRepository empresaTransporteRepository;

    // metodos GET
    @Override
    public List<Transportistas> getAllTransportistas() {
        return transportistaRepository.findAll();
    }

    @Override
    public Transportistas getTransportistaByid(Long id_transportista) {
        return transportistaRepository.findById(id_transportista)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado con id: " + id_transportista));
    }
    // --- FIN GET

    // metodos POST
    @Override
    public Transportistas saveTransportista(Transportistas transportistaNuevo) {
        // Validar que la empresa de transporte exista
        EmpresaTransporte empresa = empresaTransporteRepository
                .findById(transportistaNuevo.getEmpresa_transporte().getId_empresa_transporte())
                .orElseThrow(() -> new RuntimeException(
                        "Empresa de transporte no encontrada con id: "
                                + transportistaNuevo.getEmpresa_transporte().getId_empresa_transporte()));

        transportistaNuevo.setEmpresa_transporte(empresa);
        return transportistaRepository.save(transportistaNuevo);
    }

    // metodos PUT
    @Override
    public Transportistas putTransportista(Long id_transportista, Transportistas transportista) {
        Transportistas transportista_existente = transportistaRepository.findById(id_transportista)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado con id: " + id_transportista));

        transportista_existente.setP_nombre(transportista.getP_nombre());
        transportista_existente.setS_nombre(transportista.getS_nombre());
        transportista_existente.setP_apellido(transportista.getP_apellido());
        transportista_existente.setS_apellido(transportista.getS_apellido());
        transportista_existente.setCuerpo_rut(transportista.getCuerpo_rut());
        transportista_existente.setDv_rut(transportista.getDv_rut());

        // Validar y actualizar la empresa de transporte
        EmpresaTransporte empresa = empresaTransporteRepository
                .findById(transportista.getEmpresa_transporte().getId_empresa_transporte())
                .orElseThrow(() -> new RuntimeException(
                        "Empresa de transporte no encontrada con id: "
                                + transportista.getEmpresa_transporte().getId_empresa_transporte()));

        transportista_existente.setEmpresa_transporte(empresa);

        return transportistaRepository.save(transportista_existente);
    }

    // metodos DELETE
    @Override
    public void deleteTransportista(Long id_transportista) {
        Transportistas transportista_eliminado = transportistaRepository.findById(id_transportista)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado con id: " + id_transportista));

        transportistaRepository.delete(transportista_eliminado);
    }
}
