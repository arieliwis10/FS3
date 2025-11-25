package com.example.demo.lab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ===============================================================
 * 📘 Clase: TipoAnalisis
 * ---------------------------------------------------------------
 * Catálogo de tipos de análisis (p. ej., HEMOGRAMA, PCR, ORINA).
 * ===============================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "TIPO_ANALISIS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_TIPO_ANALISIS_NOMBRE", columnNames = "NOMBRE")
    }
)
public class TipoAnalisis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO", nullable = false)
    private Long id;

    @NotBlank(message = "El nombre del tipo es obligatorio")
    @Size(max = 80, message = "El nombre no debe exceder 80 caracteres")
    @Column(name = "NOMBRE", nullable = false, length = 80)
    private String nombre;

    @Size(max = 255)
    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;
}