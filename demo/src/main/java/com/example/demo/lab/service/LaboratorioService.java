package com.example.demo.lab.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.lab.model.Laboratorio;
import com.example.demo.lab.model.TipoAnalisis;
import com.example.demo.lab.repository.LaboratorioRepository;
import com.example.demo.lab.repository.TipoAnalisisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LaboratorioService {

    private final LaboratorioRepository labRepo;
    private final TipoAnalisisRepository tipoRepo;

    // ==================== CRUD ====================
    public List<Laboratorio> listar() {
        log.info("📋 Listando laboratorios");
        return labRepo.findAll();
    }

    public Laboratorio obtener(Long id) {
        log.info("🔍 Buscando laboratorio {}", id);
        return labRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratorio no encontrado id: " + id));
    }

    public Laboratorio crear(Laboratorio l) {
        log.info("📝 Creando laboratorio {}", l.getNombre());
        labRepo.findByNombreIgnoreCase(l.getNombre()).ifPresent(x -> {
            throw new IllegalArgumentException("Ya existe un laboratorio con ese nombre");
        });
        return labRepo.save(l);
    }

    public Laboratorio actualizar(Long id, Laboratorio nuevo) {
        Laboratorio actual = obtener(id);
        log.info("✏️ Actualizando laboratorio {}", id);
        actual.setNombre(nuevo.getNombre());
        actual.setCapacidad(nuevo.getCapacidad());
        actual.setEstado(nuevo.getEstado());
        return labRepo.save(actual);
    }

    public void eliminar(Long id) {
        log.info("🗑️ Eliminando laboratorio {}", id);
        if (!labRepo.existsById(id)) {
            throw new ResourceNotFoundException("Laboratorio no encontrado id: " + id);
        }
        labRepo.deleteById(id);
    }

    // ==================== Asignación de Tipos ====================
    @Transactional
    public Laboratorio asignarTipo(Long idLab, Long idTipo) {
        Laboratorio lab = obtener(idLab);
        TipoAnalisis tipo = tipoRepo.findById(idTipo)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de análisis no encontrado id: " + idTipo));
        log.info("🔗 Asignando tipo {} al laboratorio {}", idTipo, idLab);
        lab.getTiposAnalisis().add(tipo);
        return lab;
    }

    @Transactional
    public Laboratorio quitarTipo(Long idLab, Long idTipo) {
        Laboratorio lab = obtener(idLab);
        TipoAnalisis tipo = tipoRepo.findById(idTipo)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de análisis no encontrado id: " + idTipo));
        log.info("❌ Quitando tipo {} del laboratorio {}", idTipo, idLab);
        lab.getTiposAnalisis().remove(tipo);
        return lab;
    }

    // ==================== Consultas de apoyo ====================
    public List<Laboratorio> listarPorEstado(String estado) {
        log.info("🎯 Listando por estado {}", estado);
        return labRepo.findByEstado(estado);
    }

    public List<Laboratorio> listarPorNombreTipo(String nombreTipo) {
        log.info("🔎 Listando por tipo {}", nombreTipo);
        return labRepo.findDistinctByTiposAnalisis_NombreIgnoreCase(nombreTipo);
    }
}
