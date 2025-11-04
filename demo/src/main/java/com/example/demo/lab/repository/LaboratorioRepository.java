package com.example.demo.lab.repository;

import com.example.demo.lab.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
    Optional<Laboratorio> findByNombreIgnoreCase(String nombre);
    List<Laboratorio> findByEstado(String estado);
    // Búsqueda por nombre de tipo de análisis (propiedad navegable)
    List<Laboratorio> findDistinctByTiposAnalisis_NombreIgnoreCase(String nombreTipo);
}