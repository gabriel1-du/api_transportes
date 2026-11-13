package com.example.api_transporte.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_transporte.DTO.PedidosDTO.PedidoMapper;
import com.example.api_transporte.DTO.PedidosDTO.getPedidosDTO;
import com.example.api_transporte.DTO.PedidosDTO.putPedidoDTO;
import com.example.api_transporte.DTO.PedidosDTO.savePedidoDTO;
import com.example.api_transporte.Model.Pedidos;
import com.example.api_transporte.Model.Transportistas;
import com.example.api_transporte.Repository.PedidosRepository;
import com.example.api_transporte.Repository.TransportistaRepository;
import com.example.api_transporte.Service.PedidosService;

@Service
public class PedidosServiceImpl implements PedidosService {

    // Inyeccion de repositorios y dependencias
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private TransportistaRepository transportistaRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    // metodos GET
    @Override
    public List<getPedidosDTO> getAllPedidos() {
        return pedidosRepository.findAll().stream()
                .map(pedidoMapper::EntitytoGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public getPedidosDTO getPedidoByid(Long id_pedido) {
        return pedidoMapper.EntitytoGetDTO(pedidosRepository.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id_pedido)));
    }
    // --- FIN GET

    // metodos POST
    @Override
    public getPedidosDTO savePedido(savePedidoDTO pedidoNuevo) {
        Pedidos pedido = pedidoMapper.EntitytoSaveDTO(pedidoNuevo);

        // Validar que el transportista exista
        Transportistas transportista = transportistaRepository
                .findById(pedido.getTransportista().getId_transportista())
                .orElseThrow(() -> new RuntimeException(
                        "Transportista no encontrado con id: "
                                + pedido.getTransportista().getId_transportista()));

        pedido.setTransportista(transportista);
        return pedidoMapper.EntitytoGetDTO(pedidosRepository.save(pedido));
    }

    // metodos PUT
    @Override
    public getPedidosDTO putPedido(Long id_pedido, putPedidoDTO pedido) {
        Pedidos pedido_existente = pedidosRepository.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id_pedido));

        Pedidos cambios = pedidoMapper.putDTOtoEntity(pedido);

        // Solo actualizar los campos que vienen en el PUT (los que no sean null)
        if (cambios.getEntregado() != null) {
            pedido_existente.setEntregado(cambios.getEntregado());
        }
        if (cambios.getFecha_de_entrega() != null) {
            pedido_existente.setFecha_de_entrega(cambios.getFecha_de_entrega());
        }

        return pedidoMapper.EntitytoGetDTO(pedidosRepository.save(pedido_existente));
    }

    // metodos DELETE
    @Override
    public void deletePedido(Long id_pedido) {
        Pedidos pedido_eliminado = pedidosRepository.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id_pedido));

        pedidosRepository.delete(pedido_eliminado);
    }
}
