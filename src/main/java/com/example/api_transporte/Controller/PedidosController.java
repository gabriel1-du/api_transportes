package com.example.api_transporte.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_transporte.DTO.PedidosDTO.getPedidosDTO;
import com.example.api_transporte.DTO.PedidosDTO.putPedidoDTO;
import com.example.api_transporte.DTO.PedidosDTO.savePedidoDTO;
import com.example.api_transporte.Service.PedidosService;

@RestController
@RequestMapping("/api/PedidosApi")
public class PedidosController {

    // inyeccion del servicio
    @Autowired
    private PedidosService pedidosService;

    // metodos get
    @GetMapping("/")
    public ResponseEntity<List<getPedidosDTO>> getAllPedidos() {
        List<getPedidosDTO> pedidos = pedidosService.getAllPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id_pedido}")
    public ResponseEntity<?> getPedidoById(@PathVariable("id_pedido") Long id_pedido) {
        try {
            getPedidosDTO pedido = pedidosService.getPedidoByid(id_pedido);
            return ResponseEntity.ok(pedido);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // ----- fin metodos get

    // metodos POST
    @PostMapping("/")
    public ResponseEntity<?> savePedido(@RequestBody savePedidoDTO pedidoNuevo) {
        try {
            getPedidosDTO save = pedidosService.savePedido(pedidoNuevo);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // metodos PUT
    @PutMapping("/{id_pedido}")
    public ResponseEntity<?> putPedido(@RequestBody putPedidoDTO pedido, @PathVariable("id_pedido") Long id_pedido) {
        try {
            getPedidosDTO pedido_actualizado = pedidosService.putPedido(id_pedido, pedido);
            return ResponseEntity.ok(pedido_actualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // metodos DELETE
    @DeleteMapping("/{id_pedido}")
    public ResponseEntity<?> deletePedido(@PathVariable("id_pedido") Long id_pedido) {
        try {
            pedidosService.deletePedido(id_pedido);

            return ResponseEntity.ok("Registro eliminado exitosamente");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
