package utils;
import model.Pessoa;

import java.io.Serializable;

// Classe que implementa uma fila simples para armazenar objetos Pessoa
public class Fila implements Serializable {

    // Nó interno que representa cada elemento da fila
    private static class No implements Serializable {
        Pessoa pessoa;  // Pessoa armazenada no nó
        No proximo;     // Referência para o próximo nó

        // Construtor do nó com uma pessoa
        No(Pessoa pessoa) {
            this.pessoa = pessoa;
            this.proximo = null;
        }
    }

    private No inicio;  // Primeiro nó da fila
    private No fim;     // Último nó da fila
    private int tamanho; // Quantidade de elementos na fila

    // Adiciona uma pessoa ao final da fila
    public void enfileirar(Pessoa pessoa) {
        No novo = new No(pessoa);
        if (fim != null) {
            fim.proximo = novo;  // Liga o novo nó ao final
        } else {
            inicio = novo;  // Se vazia, define como início
        }
        fim = novo;  // Atualiza o fim
        tamanho++;   // Incrementa o tamanho
    }

    // Remove e retorna a pessoa do início da fila
    public Pessoa desenfileirar() {
        if (inicio == null) return null;  // Retorna null se vazia
        Pessoa p = inicio.pessoa;         // Pega a pessoa do início
        inicio = inicio.proximo;          // Move o início
        if (inicio == null) fim = null;   // Atualiza fim se a fila ficou vazia
        tamanho--;                        // Decrementa o tamanho
        return p;                         // Retorna a pessoa removida
    }

    // Verifica se a fila está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // Retorna o tamanho atual da fila
    public int tamanho() {
        return tamanho;
    }
}
