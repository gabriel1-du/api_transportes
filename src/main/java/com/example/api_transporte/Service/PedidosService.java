package com.example.api_transporte.Service;

import java.util.List;

import com.example.api_transporte.DTO.PedidosDTO.getPedidosDTO;
import com.example.api_transporte.DTO.PedidosDTO.putPedidoDTO;
import com.example.api_transporte.DTO.PedidosDTO.savePedidoDTO;

public interface PedidosService {

    //get
    List<getPedidosDTO> getAllPedidos();

    getPedidosDTO getPedidoByid(Long id_pedido);
    //fin gets

    getPedidosDTO savePedido(savePedidoDTO pedidoNuevo);

    getPedidosDTO putPedido(Long id_pedido, putPedidoDTO pedido);

    void deletePedido(Long id_pedido);
}
