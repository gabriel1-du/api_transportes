package com.example.api_transporte.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_transporte.Model.Transportistas;

public interface TransportistaRepository extends JpaRepository<Transportistas , Long> {

}
