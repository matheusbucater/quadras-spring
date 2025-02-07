package com.github.matheusbucater.quadras_smc.dto;


public record UsuarioCreationRequestDTO(
        String nome,
        String sobrenome,
        String descricao,
        String email,
        String senha,
        String telefone
) { }
