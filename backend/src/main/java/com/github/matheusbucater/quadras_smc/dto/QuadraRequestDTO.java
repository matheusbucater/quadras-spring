package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.entity.Reserva;
import com.github.matheusbucater.quadras_smc.enums.EstadoDaQuadra;
import com.github.matheusbucater.quadras_smc.enums.TipoDeQuadra;

import java.time.LocalDateTime;
import java.util.List;

public record QuadraRequestDTO(
        String nome,
        String descricao,
        Boolean eh_coberta,
        TipoDeQuadra tipo_de_quadra,
        EstadoDaQuadra estado_da_quadra,
        String imgurl
) {
}
