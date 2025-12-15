package com.example.demo.auth.dto;

public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String rol
) {}
