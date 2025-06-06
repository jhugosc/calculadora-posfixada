package com.github.jhugosc.calculadora_posfixada.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.github.jhugosc.calculadora_posfixada.model.FilaDinamica;
import com.github.jhugosc.calculadora_posfixada.model.Pilha;

@Service
public class CalculadoraService {

    // Método principal que realiza o cálculo da expressão pós-fixada
    public double calcularExpressao(String expressao) {
        FilaDinamica<String> fila = new FilaDinamica<>();
        Pilha<Double> pilha = new Pilha<>();

        // Divide a expressão por espaços
        String[] caracters = expressao.trim().split("\\s+");

        // Garante que há pelo menos dois operandos e um operador
        if (caracters.length < 3) {
            throw new IllegalArgumentException("Expressão malformada: deve conter ao menos dois operandos e um operador.");
        }

        // Enfileira os elementos da expressão
        for (String c : caracters) {
            fila.enfileirar(c);
        }

        // Processa a fila
        while (!fila.estaVazia()) {
            String caracter = fila.desenfileirar();

            if (ehNumero(caracter)) {
                pilha.empilhar(Double.parseDouble(caracter));
            } else if (ehOperador(caracter)) {
                if (pilha.tamanho() < 2) {
                    throw new IllegalArgumentException("Faltam operandos para o operador '" + caracter + "'.");
                }

                double n2 = pilha.desempilhar();
                double n1 = pilha.desempilhar();

                double resultado = operacaoCalculo(n1, n2, caracter);
                pilha.empilhar(resultado);
            } else {
                throw new IllegalArgumentException("Operador inválido: '" + caracter + "'");
            }
        }

        // Ao final deve restar apenas um valor na pilha
        if (pilha.tamanho() != 1) {
            throw new IllegalArgumentException("Expressão malformada: operandos ou operadores em excesso.");
        }

        return pilha.desempilhar();
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