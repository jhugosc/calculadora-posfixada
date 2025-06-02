package com.github.jhugosc.calculadora_posfixada.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.github.jhugosc.calculadora_posfixada.model.FilaDinamica;
import com.github.jhugosc.calculadora_posfixada.model.Pilha;

@Service
public class CalculadoraService {

    // Método principal que realiza o cálculo da expressão pós-fixada
    public double calcularExpressao(String expressao) {
        FilaDinamica<String> fila = new FilaDinamica<>(); // Fila para armazenar a expressão
        Pilha<Double> pilha = new Pilha<>(); // Pilha para realizar os cálculos

        // Divide a expressão por espaços e adiciona cada elemento na fila
        String[] caracters = expressao.trim().split("\\s+");
        for (int i = 0; i < caracters.length; i++) {
            fila.enfileirar(caracters[i]);
        }

        // Processa a fila até que ela esteja vazia
        while (!fila.estaVazia()) {
            String caracter = fila.desenfileirar();

            if (ehNumero(caracter)) {
                // Se for número, empilha
                pilha.empilhar(Double.parseDouble(caracter));
            } else if (ehOperador(caracter)) {
                // Se for operador, verifica se há pelo menos dois operandos na pilha
                if (pilha.tamanho() < 2) {
                    throw new IllegalArgumentException("Expressão malformada: Operandos insuficientes.");
                }

                // Desempilha dois valores para operar
                double n2 = pilha.desempilhar();
                double n1 = pilha.desempilhar();

                // Realiza a operação e empilha o resultado
                double resultado = operacaoCalculo(n1, n2, caracter);
                pilha.empilhar(resultado);
            } else {
                // Caso encontre um caracter inválido
                throw new IllegalArgumentException("Caracter inválido: " + caracter);
            }
        }

        // Ao final, deve restar exatamente um valor na pilha (o resultado)
        if (pilha.tamanho() != 1) {
            throw new IllegalArgumentException("Expressão malformada: Operandos ou operadores em excesso.");
        }

        return pilha.desempilhar(); // Retorna o resultado final
    }

    // Verifica se o valor é um número
    private boolean ehNumero(String caracter) {
        try {
            Double.parseDouble(caracter);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Conjunto de operadores válidos
    private static final Set<String> OPERADORES = Set.of("+", "-", "*", "/", "%");

    // Verifica se o valor é um operador válido
    private boolean ehOperador(String caracter) {
        return OPERADORES.contains(caracter);
    }

    // Realiza a operação matemática entre dois números, baseado no operador fornecido
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
                // Caso o operador não esteja na lista de válidos
                throw new IllegalArgumentException("Operador inválido: " + operador);
        }
        return resultado;
    }
}