package com.example.demo.resultado.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.lab.service.LaboratorioService;
import com.example.demo.resultado.model.Resultado;
import com.example.demo.resultado.repository.ResultadoRepository;
import com.example.demo.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final ResultadoRepository repository;
    private final LaboratorioService laboratorioService;
    private final UsuarioService usuarioService;

    // ==================== CRUD ====================
    public List<Resultado> listar() {
        log.info("📋 Listando resultados");
        return repository.findAll();
    }

    public Resultado obtener(Long id) {
        log.info("🔍 Buscando resultado {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resultado no encontrado id: " + id));
    }

    @Transactional
    public Resultado crear(Resultado resultado) {
        log.info("📝 Creando resultado para paciente {}", resultado.getPaciente());
        resultado.setLaboratorio(laboratorioService.obtener(resultado.getLaboratorio().getId()));
        if (resultado.getAnalista() != null && resultado.getAnalista().getId() != null) {
            resultado.setAnalista(usuarioService.buscarPorId(resultado.getAnalista().getId()));
        } else {
            resultado.setAnalista(null);
        }
        return repository.save(resultado);
    }

    @Transactional
    public Resultado actualizar(Long id, Resultado data) {
        Resultado existente = obtener(id);
        log.info("✏️ Actualizando resultado {}", id);

        existente.setPaciente(data.getPaciente());
        existente.setTipoAnalisis(data.getTipoAnalisis());
        existente.setFechaMuestra(data.getFechaMuestra());
        existente.setEstado(data.getEstado());
        existente.setObservaciones(data.getObservaciones());
        existente.setLaboratorio(laboratorioService.obtener(data.getLaboratorio().getId()));

        if (data.getAnalista() != null && data.getAnalista().getId() != null) {
            existente.setAnalista(usuarioService.buscarPorId(data.getAnalista().getId()));
        } else {
            existente.setAnalista(null);
        }

        return repository.save(existente);
    }

    public void eliminar(Long id) {
        log.info("🗑️ Eliminando resultado {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resultado no encontrado id: " + id);
        }
        repository.deleteById(id);
    }

    // ==================== Consultas ====================
    public List<Resultado> listarPorLaboratorio(Long laboratorioId) {
        log.info("🔗 Listando resultados del laboratorio {}", laboratorioId);
        return repository.findByLaboratorio_Id(laboratorioId);
    }

    public List<Resultado> listarPorAnalista(Long analistaId) {
        log.info("👤 Listando resultados del analista {}", analistaId);
        return repository.findByAnalista_Id(analistaId);
    }

    public List<Resultado> listarPorEstado(String estado) {
        log.info("🎯 Listando resultados por estado {}", estado);
        return repository.findByEstado(estado);
    }
}
