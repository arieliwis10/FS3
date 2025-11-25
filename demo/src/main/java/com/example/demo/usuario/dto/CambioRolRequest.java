package com.example.demo.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO para actualizar únicamente el rol de un usuario.
 */
@Data
public class CambioRolRequest {

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|ANALISTA", message = "Rol inválido. Solo se permite ADMIN o ANALISTA")
    private String rol;
}
