package com.github.matheusbucater.quadras_smc.enums;

import lombok.Getter;

@Getter
public enum TipoDeQuadra {
    SAIBRO("saibro"),
    DURA("dura"),
    GRAMA("grama");

    private String tipoDeQuadra;

    TipoDeQuadra(String tipoDeQuadra) {
        this.tipoDeQuadra = tipoDeQuadra;
    }

}
