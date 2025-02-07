package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.enums.TipoDeUsuario;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String sobrenome,
        String descricao,
        String email,
        String telefone,
        TipoDeUsuario tipo_de_usuario,
        String verify_account_token,
        LocalDateTime verified_at,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        LocalDateTime deleted_at
) {
    public static UsuarioResponseDTO from(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getDescricao(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipoDeUsuario(),
                null,
                usuario.getVerifiedAt(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt(),
                usuario.getDeletedAt()
        );
    }
    public static UsuarioResponseDTO from(Usuario usuario, String token) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getDescricao(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipoDeUsuario(),
                token,
                usuario.getVerifiedAt(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt(),
                usuario.getDeletedAt()
        );
    }
}
