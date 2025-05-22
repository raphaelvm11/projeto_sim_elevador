package utils;

// Classe que representa uma lista encadeada simples sem usar estruturas prontas do Java
public class Lista {
    private Nodo inicio;   // Primeiro nó da lista
    private Nodo fim;      // Último nó da lista
    private int tamanho;   // Contador de elementos

    // Construtor que inicializa uma lista vazia
    public Lista() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    // Insere um novo objeto no final da lista
    public void inserirFim(Object objeto) {
        Nodo novoNodo = new Nodo(objeto);
        if (fim == null) {
            inicio = fim = novoNodo;  // Lista vazia
        } else {
            fim.setProximo(novoNodo);
            fim = novoNodo;
        }
        tamanho++;
    }

    // Obtém o objeto em uma posição específica
    public Object get(int indice) {
        if (indice >= tamanho || indice < 0) {
            throw new IndexOutOfBoundsException("Índice fora dos limites");
        }
        Nodo atual = inicio;
        for (int i = 0; i < indice; i++) {
            atual = atual.getProximo();
        }
        return atual.getObjeto();
    }

    // Retorna o tamanho da lista
    public int tamanho() {
        return tamanho;
    }

    // Remove o primeiro objeto igual ao fornecido
    public boolean remover(Object objeto) {
        if (inicio == null) return false;  // Lista vazia
        if (inicio.getObjeto().equals(objeto)) {
            inicio = inicio.getProximo();
            if (inicio == null) fim = null;
            tamanho--;
            return true;
        }
        Nodo atual = inicio;
        while (atual.getProximo() != null) {
            if (atual.getProximo().getObjeto().equals(objeto)) {
                atual.setProximo(atual.getProximo().getProximo());
                if (atual.getProximo() == null) fim = atual;
                tamanho--;
                return true;
            }
            atual = atual.getProximo();
        }
        return false;  // Objeto não encontrado
    }
}
