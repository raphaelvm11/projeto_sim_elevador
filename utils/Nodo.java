package utils;

// Classe que representa um nó de uma lista encadeada
public class Nodo {
    private Object objeto;   // Objeto armazenado no nó
    private Nodo proximo;    // Referência para o próximo nó

    // Construtor que inicializa o nó com um objeto
    public Nodo(Object objeto) {
        this.objeto = objeto;
        this.proximo = null;
    }

    // Retorna o objeto armazenado
    public Object getObjeto() {
        return objeto;
    }

    // Retorna o próximo nó
    public Nodo getProximo() {
        return proximo;
    }

    // Define o próximo nó
    public void setProximo(Nodo proximo) {
        this.proximo = proximo;
    }
}
