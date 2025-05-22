package com.github.jhugosc.calculadora_posfixada.model;

import java.util.NoSuchElementException;

public class Pilha<T> {
    private static class Node<T> {
        T valor;
        Node<T> proximo;

        Node(T valor) {
            this.valor = valor;
        }
    }

    private Node<T> topo;
    private int tamanho;

    public void empilhar(T valor) {
        Node<T> novo = new Node<>(valor);
        novo.proximo = topo;
        topo = novo;
        tamanho++;
    }

    public T desempilhar() {
        if (topo == null) {
            throw new NoSuchElementException("Pilha vazia");
        }
        T valor = topo.valor;
        topo = topo.proximo;
        tamanho--;
        return valor;
    }

    public T topo() {
        if (topo == null) {
            throw new NoSuchElementException("Pilha vazia");
        }
        return topo.valor;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }
}
