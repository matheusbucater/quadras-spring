package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.entity.Reserva;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.enums.TipoDeReserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public record ReservaResponseDTO(
        Long id,
        UsuarioResponseDTO dono_da_reserva,
        List<UsuarioResponseDTO> jogadores,
        QuadraResponseDTO quadra,
        TipoDeReserva tipo_de_reserva,
        Integer num_jogadores,
        String descricao,
        Boolean eh_publico,
        LocalDate dt_reserva,
        LocalTime hr_inicio,
        LocalTime hr_fim,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        LocalDateTime deleted_at
) {

    public static ReservaResponseDTO from(Reserva reserva) {

        List<UsuarioResponseDTO> jogadores = reserva.getJogadores()
                .stream()
                .map(UsuarioResponseDTO::from)
                .toList();

        QuadraResponseDTO quadra = QuadraResponseDTO.from(reserva.getQuadra());

        UsuarioResponseDTO donoDaReserva = UsuarioResponseDTO.from(reserva.getDonoDaReserva());

        return new ReservaResponseDTO(
                reserva.getId(),
                donoDaReserva,
                jogadores,
                quadra,
                reserva.getTipoDeReserva(),
                reserva.getNumJogadores(),
                reserva.getDescricao(),
                reserva.getEhPublica(),
                reserva.getDtReserva(),
                reserva.getHrInicio(),
                reserva.getHrFim(),
                reserva.getCreatedAt(),
                reserva.getUpdatedAt(),
                reserva.getDeletedAt()
        );
    }
}
