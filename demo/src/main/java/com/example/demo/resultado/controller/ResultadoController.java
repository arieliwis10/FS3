package com.example.demo.resultado.controller;

import com.example.demo.resultado.model.Resultado;
import com.example.demo.resultado.service.ResultadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/resultados")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ResultadoController {

    private final ResultadoService service;

    // ---------- CRUD ----------
    @GetMapping
    public ResponseEntity<List<Resultado>> listar() {
        log.info("📥 [GET] /api/resultados");
        List<Resultado> data = service.listar();
        return data.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resultado> obtener(@PathVariable Long id) {
        log.info("📥 [GET] /api/resultados/{}", id);
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<Resultado> crear(@Valid @RequestBody Resultado r) {
        log.info("📤 [POST] /api/resultados");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(r));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resultado> actualizar(@PathVariable Long id, @Valid @RequestBody Resultado r) {
        log.info("✏️ [PUT] /api/resultados/{}", id);
        return ResponseEntity.ok(service.actualizar(id, r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("🗑️ [DELETE] /api/resultados/{}", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Consultas de apoyo ----------
    @GetMapping("/laboratorio/{laboratorioId}")
    public ResponseEntity<List<Resultado>> listarPorLaboratorio(@PathVariable Long laboratorioId) {
        log.info("🎯 [GET] /api/resultados/laboratorio/{}", laboratorioId);
        List<Resultado> data = service.listarPorLaboratorio(laboratorioId);
        return data.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(data);
    }

    @GetMapping("/analista/{analistaId}")
    public ResponseEntity<List<Resultado>> listarPorAnalista(@PathVariable Long analistaId) {
        log.info("🎯 [GET] /api/resultados/analista/{}", analistaId);
        List<Resultado> data = service.listarPorAnalista(analistaId);
        return data.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(data);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Resultado>> listarPorEstado(@PathVariable String estado) {
        log.info("🎯 [GET] /api/resultados/estado/{}", estado);
        List<Resultado> data = service.listarPorEstado(estado);
        return data.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(data);
    }
}
