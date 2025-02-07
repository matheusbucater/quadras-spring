package com.github.matheusbucater.quadras_smc.enums;

public enum TipoDeUsuario {

    JOGADOR("jogador"),
    PROFESSOR("professor"),
    ADMINISTRADOR("admin"),
    SUPERVISOR("supervisor");

    private String tipoDeUsuario;

    TipoDeUsuario(String tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
    }

    public String getTipoDeUsuario() {
        return tipoDeUsuario;
    }
}
