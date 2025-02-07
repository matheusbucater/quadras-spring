package com.github.matheusbucater.quadras_smc.dto;


public record UsuarioUpdateRequestDTO(
        String nome,
        String sobrenome,
        String descricao,
        String email,
        String telefone
) { }
