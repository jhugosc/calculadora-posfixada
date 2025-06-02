package com.github.jhugosc.calculadora_posfixada.dto;

public class ResultadoResponseDTO {
    private double resultado;

    public ResultadoResponseDTO() {
    }

    public ResultadoResponseDTO(double resultado) {
        this.resultado = resultado;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }
}