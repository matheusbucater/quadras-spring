package com.github.matheusbucater.quadras_smc.enums;

import lombok.Getter;

@Getter
public enum EstadoDaQuadra{
    DISPONIVEL("saibro"),
    EM_REFORMA("dura");

    private String estadoDaQuadra;

    EstadoDaQuadra(String estadoDaQuadra) {
        this.estadoDaQuadra = estadoDaQuadra;
    }

}
