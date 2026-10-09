package com.example.api_transporte.DTO.PedidosDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.api_transporte.DTO.RestClientDTO.UsuarioExternoDTO;
import com.example.api_transporte.Model.Pedidos;
import com.example.api_transporte.Model.Transportistas;
import com.example.api_transporte.RestClient.UsuarioClient;

@Component
public class PedidoMapper {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Inyeccion del cliente REST de usuarios
    @Autowired
    private UsuarioClient usuarioClient;

    // Inyeccion del cliente REST de boletas (para corroborar que la boleta existe)
    @Autowired
    @Qualifier("boletasRestClient")
    private RestClient boletasRestClient;

    // ---------------------------------------------------------------
    // Entidad Pedidos -> getPedidosDTO
    // ---------------------------------------------------------------
    public getPedidosDTO EntitytoGetDTO(Pedidos pedido) {

        getPedidosDTO dto = new getPedidosDTO();
        dto.setId_pedido(pedido.getId_pedido());
        dto.setId_usuario(pedido.getId_usuario_cliente());

        // Corroborar externamente los datos del usuario
        UsuarioExternoDTO usuario = usuarioClient.getUsuarioById(pedido.getId_usuario_cliente());
        if (usuario != null) {
            dto.setNombres(usuario.getP_nombre() + " " + usuario.getS_nombre());
            dto.setApellidos(usuario.getP_apellido() + " " + usuario.getS_apellido());
            // El rut de la API ya incluye el dígito verificador (ej: "19876543-2")
            dto.setRut_completo(usuario.getRut());
        } else {
            dto.setNombres("Desconocido");
            dto.setApellidos("Desconocido");
            dto.setRut_completo("Desconocido");
        }

        // Formateo de fechas
        if (pedido.getFecha_de_envio() != null) {
            dto.setFecha_envio(pedido.getFecha_de_envio().format(FORMATO_FECHA));
        }
        if (pedido.getFecha_de_entrega() != null) {
            dto.setFecha_entregado(pedido.getFecha_de_entrega().format(FORMATO_FECHA));
        } else {
            dto.setFecha_entregado("Pendiente");
        }

        dto.setEntregado(pedido.getEntregado());

        return dto;
    }

    // ---------------------------------------------------------------
    // savePedidoDTO -> Entidad Pedidos
    // ---------------------------------------------------------------
    public Pedidos EntitytoSaveDTO(savePedidoDTO dto) {

        // Corroborar externamente que el usuario existe
        UsuarioExternoDTO usuario = usuarioClient.getUsuarioById(dto.getId_usuario());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + dto.getId_usuario());
        }

        // Corroborar externamente que la boleta existe
        Boolean boletaExiste = boletasRestClient.get()
                .uri("/{id_boleta}", dto.getId_boleta())
                .retrieve()
                .toBodilessEntity()
                .getStatusCode()
                .is2xxSuccessful();
        if (!boletaExiste) {
            throw new RuntimeException("Boleta no encontrada con id: " + dto.getId_boleta());
        }

        // Construir la fecha de envio a partir de anio, mes, dia y hora
        if (dto.getHora() == null || dto.getHora() < 0 || dto.getHora() > 23) {
            throw new RuntimeException("La hora debe estar entre 0 y 23");
        }
        if (dto.getMes() == null || dto.getMes() < 1 || dto.getMes() > 12) {
            throw new RuntimeException("El mes debe estar entre 1 y 12");
        }
        LocalDateTime fechaEnvio = LocalDateTime.of(
                dto.getAnio(),
                dto.getMes(),
                dto.getDia(),
                dto.getHora(),
                0);

        Pedidos nuevoPedido = new Pedidos();
        nuevoPedido.setId_usuario_cliente(dto.getId_usuario());
        nuevoPedido.setId_boleta(dto.getId_boleta());
        nuevoPedido.setFecha_de_envio(fechaEnvio);
        nuevoPedido.setEntregado(false);

        // Transportista asignado (el servicio corroborara que existe en BD)
        Transportistas transportista = new Transportistas();
        transportista.setId_transportista(dto.getId_transportista());
        nuevoPedido.setTransportista(transportista);

        return nuevoPedido;
    }

    // ---------------------------------------------------------------
    // putPedidoDTO -> Entidad Pedidos
    // ---------------------------------------------------------------
    public Pedidos putDTOtoEntity(putPedidoDTO dto) {

        Pedidos pedido = new Pedidos();
        pedido.setEntregado(dto.isEstado_entrega());

        // Si se marca como entregado, se setea la fecha actual como fecha de entrega
        if (dto.isEstado_entrega()) {
            pedido.setFecha_de_entrega(LocalDateTime.now());
        } else {
            pedido.setFecha_de_entrega(null);
        }

        return pedido;
    }

}
