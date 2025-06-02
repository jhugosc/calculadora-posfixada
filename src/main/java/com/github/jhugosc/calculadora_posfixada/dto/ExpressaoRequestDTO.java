package com.github.jhugosc.calculadora_posfixada.dto;

public class ExpressaoRequestDTO {
    private String expressao;

    public ExpressaoRequestDTO() {
    }

    public ExpressaoRequestDTO(String expressao) {
        this.expressao = expressao;
    }

    public String getExpressao() {
        return expressao;
    }

    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }
}
