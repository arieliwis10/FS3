package com.example.demo.lab.repository;

import com.example.demo.lab.model.TipoAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoAnalisisRepository extends JpaRepository<TipoAnalisis, Long> {
    Optional<TipoAnalisis> findByNombreIgnoreCase(String nombre);
}
