package com.example.demo.auth.controller;

import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.UsuarioResponse;
import com.example.demo.usuario.model.Usuario;
import com.example.demo.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> login(@Valid @RequestBody LoginRequest req) {

        Usuario u = usuarioService.buscarPorEmail(req.email());

        // ✅ Login simple (texto plano) solo para tu proyecto/entrega
        if (!u.getPassword().equals(req.password())) {
            return ResponseEntity.status(401).build();
        }

        UsuarioResponse resp = new UsuarioResponse(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getTelefono(),
                u.getRol()
        );

        return ResponseEntity.ok(resp);
    }
}
