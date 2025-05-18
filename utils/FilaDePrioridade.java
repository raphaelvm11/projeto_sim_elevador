package utils;
import model.Pessoa;

import java.io.Serializable;

// Implementa uma fila com prioridade para pessoas idosas ou cadeirantes
public class FilaDePrioridade implements Serializable {
    private No inicio;    // Primeiro nó da fila
    private No fim;       // Último nó da fila
    private int tamanho;  // Quantidade de elementos

    // Nó interno contendo a pessoa e o próximo nó
    private class No {
        Pessoa pessoa;
        No proximo;

        No(Pessoa pessoa) {
            this.pessoa = pessoa;
            this.proximo = null;
        }
    }

    // Adiciona uma pessoa respeitando a prioridade
    public void enfileirar(Pessoa pessoa) {
        No novo = new No(pessoa);
        if (inicio == null) {
            inicio = fim = novo;  // Fila vazia
        } else if (temPrioridade(pessoa)) {
            novo.proximo = inicio;  // Insere no início se tiver prioridade
            inicio = novo;
        } else {
            No atual = inicio;
            while (atual.proximo != null && temPrioridade(atual.proximo.pessoa)) {
                atual = atual.proximo;  // Encontra o ponto de inserção
            }
            novo.proximo = atual.proximo;
            atual.proximo = novo;
            if (novo.proximo == null) fim = novo;  // Atualiza fim se for o último
        }
        tamanho++;
    }

    // Remove e retorna a pessoa do início
    public Pessoa desenfileirar() {
        if (inicio == null) return null;
        Pessoa p = inicio.pessoa;
        inicio = inicio.proximo;
        if (inicio == null) fim = null;
        tamanho--;
        return p;
    }

    // Verifica se a fila está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // Retorna o tamanho da fila
    public int tamanho() {
        return tamanho;
    }

    // Verifica se a pessoa tem prioridade (idade >= 60 ou cadeirante)
    private boolean temPrioridade(Pessoa p) {
        return p.getIdade() >= 60 || p.isCadeirante();
    }
}
