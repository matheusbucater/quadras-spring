package com.github.matheusbucater.quadras_smc.entity;

import com.github.matheusbucater.quadras_smc.enums.DiaDaSemana;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "professor_horarios")
public class ProfessorHorario {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Usuario professor;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_da_semana", nullable = false)
    private DiaDaSemana diaDaSemana;

    @Column(name = "hr_inicio", nullable = false)
    private LocalTime hrInicio;

    @Column(name = "hr_fim", nullable = false)
    private LocalTime hrFim;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public ProfessorHorario(Usuario professor, DiaDaSemana diaDaSemana, LocalTime hrInicio, LocalTime hrFim) {
        this.professor = professor;
        this.diaDaSemana = diaDaSemana;
        this.hrInicio = hrInicio;
        this.hrFim = hrFim;
    }
}
