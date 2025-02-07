package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.entity.QuadraHorario;
import com.github.matheusbucater.quadras_smc.entity.Reserva;
import com.github.matheusbucater.quadras_smc.enums.EstadoDaQuadra;
import com.github.matheusbucater.quadras_smc.enums.TipoDeQuadra;

import java.time.LocalDateTime;
import java.util.List;

public record QuadraResponseDTO(
        Long id,
        String nome,
        String descricao,
        Boolean eh_coberta,
        TipoDeQuadra tipo_de_quadra,
        EstadoDaQuadra estado_da_quadra,
        String imgurl,
        List<HorarioResponseDTO> horarios,
        List<Reserva> reservas,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        LocalDateTime deleted_at
) {

    public static QuadraResponseDTO from(Quadra quadra) {

        List<HorarioResponseDTO> horarios;
        List<QuadraHorario> quadraHorarios = quadra.getHorarios();

        if (quadraHorarios == null) {
            horarios = List.of();
        } else {
            horarios = quadraHorarios.stream()
                    .map(HorarioResponseDTO::from)
                    .toList();
        }

        return new QuadraResponseDTO(
                quadra.getId(),
                quadra.getNome(),
                quadra.getDescricao(),
                quadra.getEhCoberta(),
                quadra.getTipoDeQuadra(),
                quadra.getEstadoDaQuadra(),
                quadra.getImgurl(),
                horarios,
                quadra.getReservas(),
                quadra.getCreatedAt(),
                quadra.getUpdatedAt(),
                quadra.getDeletedAt()
        );
    }
}
