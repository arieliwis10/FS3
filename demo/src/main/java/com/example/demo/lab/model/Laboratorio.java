package com.example.demo.lab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ===============================================================
 * 📘 Clase: Laboratorio
 * ---------------------------------------------------------------
 * Representa un laboratorio donde se realizan análisis clínicos.
 * Reglas solicitadas:
 *  - Validar nombre, capacidad y estado.
 *  - Asignar tipos de análisis (ManyToMany).
 * ===============================================================
 */
@Data
@Entity
@Table(
    name = "LABORATORIO",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_LABORATORIO_NOMBRE", columnNames = "NOMBRE")
    }
)
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle: usa identity o secuencia+trigger
    @Column(name = "ID_LAB", nullable = false)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no debe exceder 120 caracteres")
    @Column(name = "NOMBRE", nullable = false, length = 120)
    private String nombre;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1")
    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    /**
     * Estados permitidos:
     *  - ACTIVO: puede recibir solicitudes
     *  - INACTIVO: no recibe
     *  - MANTENCION: temporalmente fuera de servicio
     */
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO|MANTENCION",
             message = "Estado inválido (use ACTIVO, INACTIVO o MANTENCION)")
    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;

    // Relación con tipos de análisis
    @ManyToMany
    @JoinTable(
        name = "LAB_TIPO_ANALISIS",
        joinColumns = @JoinColumn(name = "ID_LAB"),
        inverseJoinColumns = @JoinColumn(name = "ID_TIPO")
    )
    private Set<TipoAnalisis> tiposAnalisis = new LinkedHashSet<>();
}