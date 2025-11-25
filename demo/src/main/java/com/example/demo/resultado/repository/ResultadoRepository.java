package com.example.demo.resultado.repository;

import com.example.demo.resultado.model.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    List<Resultado> findByLaboratorio_Id(Long laboratorioId);

    List<Resultado> findByAnalista_Id(Long analistaId);

    List<Resultado> findByEstado(String estado);
}
