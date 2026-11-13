package com.example.api_transporte.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PEDIDOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id_pedido;

    // Referencia lógica externa (microservicio de usuarios), sin FK física
    @Column(name = "id_usuario_cliente", nullable = false)
    private Long id_usuario_cliente;

    @ManyToOne
    @JoinColumn(name = "id_transportista", nullable = false)
    private Transportistas transportista;

    // Referencia lógica externa (microservicio de boletas), sin FK física
    @Column(name = "id_boleta", nullable = false)
    private Long id_boleta;

    @Column(name = "fecha_de_envio", nullable = false)
    private LocalDateTime fecha_de_envio;

    @Column(name = "fecha_de_entrega")
    private LocalDateTime fecha_de_entrega;

    @Column(name = "entregado", nullable = false)
    private Boolean entregado = false;
}
