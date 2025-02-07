package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.enums.DiaDaSemana;

import java.time.LocalTime;

public record HorarioRequestDTO(
        DiaDaSemana dia_da_semana,
        LocalTime hr_inicio,
        LocalTime hr_fim) {
}
