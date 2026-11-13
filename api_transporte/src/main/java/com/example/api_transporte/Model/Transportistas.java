package com.example.api_transporte.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TRANSPORTISTAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transportistas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transportista")
    private Long id_transportista;

    @ManyToOne
    @JoinColumn(name = "id_empresa_transporte", nullable = false)
    private EmpresaTransporte empresa_transporte;

    @Column(name = "p_nombre", nullable = false, length = 10)
    private String p_nombre;

    @Column(name = "s_nombre", nullable = false, length = 10)
    private String s_nombre;

    @Column(name = "p_apellido", nullable = false, length = 20)
    private String p_apellido;

    @Column(name = "s_apellido", nullable = false, length = 20)
    private String s_apellido;

    @Column(name = "cuerpo_rut", nullable = false, length = 9)
    private String cuerpo_rut;

    @Column(name = "dv_rut", nullable = false, length = 1)
    private String dv_rut;

    @OneToMany(mappedBy = "transportista")
    @JsonIgnore
    private List<Pedidos> pedidos;
}
