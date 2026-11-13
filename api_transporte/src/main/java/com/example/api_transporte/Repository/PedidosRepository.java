package com.example.api_transporte.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_transporte.Model.Pedidos;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {

}
