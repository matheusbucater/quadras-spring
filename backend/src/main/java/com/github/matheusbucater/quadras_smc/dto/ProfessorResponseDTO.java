package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record ProfessorResponseDTO(
        Long id,
        String nome,
        String sobrenome,
        String descricao,
        String email,
        String telefone,
        List<HorarioResponseDTO> horarios,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        LocalDateTime deleted_at
) {

    public static ProfessorResponseDTO from(Usuario usuario) {

        List<HorarioResponseDTO> horarios = usuario.getHorarios()
                .stream()
                .map(HorarioResponseDTO::from)
                .toList();

        return new ProfessorResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getDescricao(),
                usuario.getEmail(),
                usuario.getTelefone(),
                horarios,
                usuario.getCreatedAt(),
                usuario.getUpdatedAt(),
                usuario.getDeletedAt()
        );
    }
}
