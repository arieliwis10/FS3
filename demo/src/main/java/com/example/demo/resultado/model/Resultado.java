package com.example.demo.resultado.model;

import com.example.demo.lab.model.Laboratorio;
import com.example.demo.usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * ===============================================================
 * 📘 Clase: Resultado
 * ---------------------------------------------------------------
 * Representa el resultado de un análisis clínico. Se relaciona con
 * un laboratorio y opcionalmente con el usuario que registró el
 * resultado (analista).
 * ===============================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RESULTADO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESULTADO")
    private Long id;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(max = 120, message = "El nombre del paciente no debe exceder 120 caracteres")
    @Column(name = "PACIENTE", nullable = false, length = 120)
    private String paciente;

    @NotBlank(message = "El tipo de análisis es obligatorio")
    @Size(max = 100, message = "El tipo de análisis no debe exceder 100 caracteres")
    @Column(name = "TIPO_ANALISIS", nullable = false, length = 100)
    private String tipoAnalisis;

    @PastOrPresent(message = "La fecha de muestra no puede ser futura")
    @Column(name = "FECHA_MUESTRA", nullable = false)
    private LocalDate fechaMuestra = LocalDate.now();

    @Pattern(
            regexp = "PENDIENTE|EN_PROCESO|COMPLETADO|ENTREGADO",
            message = "Estado inválido (use PENDIENTE, EN_PROCESO, COMPLETADO o ENTREGADO)"
    )
    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado = "PENDIENTE";

    @Size(max = 255, message = "Las observaciones no deben exceder 255 caracteres")
    @Column(name = "OBSERVACIONES", length = 255)
    private String observaciones;

    @NotNull(message = "El laboratorio es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LAB", nullable = false)
    private Laboratorio laboratorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario analista;
}
