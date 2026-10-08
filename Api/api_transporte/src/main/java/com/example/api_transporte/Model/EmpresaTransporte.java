package com.example.api_transporte.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMPRESA_TRANSPORTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaTransporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa_transporte")
    private Long idEmpresaTransporte;

    @Column(name = "nombre_empresa", nullable = false, length = 30)
    private String nombreEmpresa;

}
