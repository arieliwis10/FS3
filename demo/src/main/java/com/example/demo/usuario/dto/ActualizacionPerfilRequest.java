package com.example.demo.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para actualizar datos visibles del perfil.
 */
@Data
public class ActualizacionPerfilRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "El teléfono debe contener entre 9 y 15 dígitos")
    private String telefono;
}
