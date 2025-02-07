package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.entity.ProfessorHorario;
import com.github.matheusbucater.quadras_smc.entity.QuadraHorario;
import com.github.matheusbucater.quadras_smc.enums.DiaDaSemana;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record HorarioResponseDTO(
        Long id,
        DiaDaSemana dia_da_semana,
        LocalTime hr_inicio,
        LocalTime hr_fim,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        LocalDateTime deleted_at
) {

    public static HorarioResponseDTO from(QuadraHorario quadraHorario) {

        return new HorarioResponseDTO(
                quadraHorario.getId(),
                quadraHorario.getDiaDaSemana(),
                quadraHorario.getHrInicio(),
                quadraHorario.getHrFim(),
                quadraHorario.getCreatedAt(),
                quadraHorario.getUpdatedAt(),
                quadraHorario.getDeletedAt()
        );
    }

    public static HorarioResponseDTO from(ProfessorHorario professorHorario) {

        return new HorarioResponseDTO(
                professorHorario.getId(),
                professorHorario.getDiaDaSemana(),
                professorHorario.getHrInicio(),
                professorHorario.getHrFim(),
                professorHorario.getCreatedAt(),
                professorHorario.getUpdatedAt(),
                professorHorario.getDeletedAt()
        );
    }
}