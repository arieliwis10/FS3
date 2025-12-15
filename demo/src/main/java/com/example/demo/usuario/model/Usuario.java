package com.example.demo.usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * ===============================================================
 * 📘 Clase: Usuario
 * ---------------------------------------------------------------
 * Representa un usuario del sistema (tabla USUARIO en Oracle).
 * Incluye credenciales básicas para autenticación simple.
 * ===============================================================
 */
@Data
@Entity
@Table(name = "USUARIO", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USUARIO_EMAIL", columnNames = "EMAIL")
})
public class Usuario {

    // ============================================================
    // 🔸 Identificador primario
    // ============================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================
    // 🔸 Datos personales
    // ============================================================

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Column(nullable = false, length = 120, unique = true)
    private String email;

    /**
     * Contraseña del usuario.
     * - Existe en BD
     * - Se puede RECIBIR en POST/PUT
     * - NO se expone en respuestas JSON
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "PASSWORD", nullable = false, length = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Pattern(
        regexp = "^\\+?[0-9]{9,15}$",
        message = "El teléfono debe contener entre 9 y 15 dígitos"
    )
    @Column(length = 20)
    private String telefono;

    @PastOrPresent(message = "La fecha de registro no puede ser futura")
    @Column(name = "FECHA_REGISTRO")
    private LocalDate fechaRegistro = LocalDate.now();

    /**
     * Rol funcional
     * (ajustado a tu BD real)
     */
    @NotBlank(message = "El rol es obligatorio")
    @Pattern(
        regexp = "ADMIN|TECNICO|RECEPCION",
        message = "Rol inválido"
    )
    @Column(nullable = false, length = 20)
    private String rol;

    @Column(nullable = false)
    private Integer estado = 1;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
}
