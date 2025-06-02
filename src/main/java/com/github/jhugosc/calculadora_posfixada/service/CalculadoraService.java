package com.github.jhugosc.calculadora_posfixada.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.github.jhugosc.calculadora_posfixada.model.FilaDinamica;
import com.github.jhugosc.calculadora_posfixada.model.Pilha;

@Service
public class CalculadoraService {

    public double calcularExpressao(String expressao) {
        FilaDinamica<String> fila = new FilaDinamica<>();
        Pilha<Double> pilha = new Pilha<>();

        String[] caracters = expressao.trim().split("\\s+");
        for (int i = 0; i < caracters.length; i++) {
            fila.enfileirar(caracters[i]);
        }

        while (!fila.estaVazia()) {
            String caracter = fila.desenfileirar();

            if (ehNumero(caracter)) {
                pilha.empilhar(Double.parseDouble(caracter));
            } else if (ehOperador(caracter)) {
                if (pilha.tamanho() < 2) {
                    throw new IllegalArgumentException("Expressão malformada: operandos insuficientes.");
                }

                double n2 = pilha.desempilhar();
                double n1 = pilha.desempilhar();
                double resultado = operacaoCalculo(n1, n2, caracter);
                pilha.empilhar(resultado);
            } else {
                throw new IllegalArgumentException("Caracter inválido: " + caracter);
            }
        }

        if (pilha.tamanho() != 1) {
            throw new IllegalArgumentException("Expressão malformada: operandos ou operadores em excesso.");
        }

        return pilha.desempilhar();
    }

    private boolean ehNumero(String caracter) {
        try {
            Double.parseDouble(caracter);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static final Set<String> OPERADORES = Set.of("+", "-", "*", "/", "%");

    private boolean ehOperador(String caracter) {
    return OPERADORES.contains(caracter);
    }


    private double operacaoCalculo(double n1, double n2, String operador) {
        double resultado;
        switch (operador) {
            case "+":
                resultado = n1 + n2;
                break;
            case "-":
                resultado = n1 - n2;
                break;
            case "*":
                resultado = n1 * n2;
                break;
            case "/":
                if (n2 == 0) {
                    throw new ArithmeticException("Divisão por zero.");
                }
                resultado = n1 / n2;
                break;
            case "%":
                if (n2 == 0) {
                    throw new ArithmeticException("Módulo por zero.");
                }
                resultado = n1 % n2;
                break;
            default:
                throw new IllegalArgumentException("Operador inválido: " + operador);
        }
        return resultado;
    }
}
