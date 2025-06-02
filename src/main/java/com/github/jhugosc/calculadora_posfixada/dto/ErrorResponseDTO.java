package com.github.jhugosc.calculadora_posfixada.dto;

public class ErrorResponseDTO {
    private String mensagem;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
