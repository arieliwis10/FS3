package com.example.demo.lab.controller;

import com.example.demo.lab.model.Laboratorio;
import com.example.demo.lab.service.LaboratorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ===============================================================
 * 📘 Clase: LaboratorioController
 * ---------------------------------------------------------------
 * Endpoints RESTful CRUD + asignación de tipos.
 * Rutas base: /api/laboratorios
 * ===============================================================
 */
@Slf4j
@RestController
@RequestMapping("/api/laboratorios")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // 👈 AGREGA ESTO
public class LaboratorioController {

    private final LaboratorioService service;

    // ---------- CRUD ----------
    @GetMapping
    public ResponseEntity<List<Laboratorio>> listar() {
        log.info("📥 [GET] /api/laboratorios");
        List<Laboratorio> labs = service.listar();
        return labs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(labs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laboratorio> obtener(@PathVariable Long id) {
        log.info("📥 [GET] /api/laboratorios/{}", id);
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<Laboratorio> crear(@Valid @RequestBody Laboratorio l) {
        log.info("📤 [POST] /api/laboratorios");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(l));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Laboratorio> actualizar(@PathVariable Long id, @Valid @RequestBody Laboratorio l) {
        log.info("✏️ [PUT] /api/laboratorios/{}", id);
        return ResponseEntity.ok(service.actualizar(id, l));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("🗑️ [DELETE] /api/laboratorios/{}", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Asignación de Tipos ----------
    @PostMapping("/{idLab}/tipos/{idTipo}")
    public ResponseEntity<Laboratorio> asignar(@PathVariable Long idLab, @PathVariable Long idTipo) {
        log.info("🔗 [POST] /api/laboratorios/{}/tipos/{}", idLab, idTipo);
        return ResponseEntity.ok(service.asignarTipo(idLab, idTipo));
    }

    @DeleteMapping("/{idLab}/tipos/{idTipo}")
    public ResponseEntity<Laboratorio> quitar(@PathVariable Long idLab, @PathVariable Long idTipo) {
        log.info("❌ [DELETE] /api/laboratorios/{}/tipos/{}", idLab, idTipo);
        return ResponseEntity.ok(service.quitarTipo(idLab, idTipo));
    }

    // ---------- Consultas de apoyo ----------
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Laboratorio>> listarPorEstado(@PathVariable String estado) {
        log.info("🎯 [GET] /api/laboratorios/estado/{}", estado);
        List<Laboratorio> data = service.listarPorEstado(estado);
        return data.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(data);
    }

    @GetMapping("/tipo/{nombreTipo}")
    public ResponseEntity<List<Laboratorio>> listarPorTipo(@PathVariable String nombreTipo) {
        log.info("🔎 [GET] /api/laboratorios/tipo/{}", nombreTipo);
        List<Laboratorio> data = service.listarPorNombreTipo(nombreTipo);
        return data.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(data);
    }
}
