package com.github.jhugosc.calculadora_posfixada.model;

import java.util.NoSuchElementException;

public class FilaDinamica<T> {
    private static class Node<T> {
        T valor;
        Node<T> proximo;

        Node(T valor) {
            this.valor = valor;
        }
    }

    private Node<T> inicio;
    private Node<T> fim;
    private int tamanho;

    public void enfileirar(T valor) {
        Node<T> novo = new Node<>(valor);
        if (fim != null) {
            fim.proximo = novo;
        }
        fim = novo;
        if (inicio == null) {
            inicio = fim;
        }
        tamanho++;
    }

    public T desenfileirar() {
        if (inicio == null) {
            throw new NoSuchElementException("Fila vazia");
        }
        T valor = inicio.valor;
        inicio = inicio.proximo;
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return valor;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }
}

