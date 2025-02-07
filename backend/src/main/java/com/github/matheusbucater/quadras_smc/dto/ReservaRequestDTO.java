package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.enums.TipoDeReserva;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaRequestDTO(
        Long id_dono,
        Long id_quadra,
        TipoDeReserva tipo_de_reserva,
        Integer num_jogadores,
        String descricao,
        Boolean eh_publico,
        LocalDate dt_reserva,
        LocalTime hr_inicio,
        LocalTime hr_fim
) {
}
