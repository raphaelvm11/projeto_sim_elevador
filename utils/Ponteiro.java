package utils;

// Classe que representa um ponteiro para navegar em uma lista encadeada
public class Ponteiro {
    private Nodo atual;  // Nó atual apontado

    // Construtor que inicializa o ponteiro com um nó
    public Ponteiro(Nodo no) {
        this.atual = no;
    }

    // Retorna o objeto do nó atual
    public Object getElemento() {
        return atual != null ? atual.getObjeto() : null;
    }

    // Retorna um novo ponteiro para o próximo nó
    public Ponteiro getProximo() {
        return atual != null ? new Ponteiro(atual.getProximo()) : null;
    }

    // Verifica se o ponteiro aponta para um nó válido
    public boolean isValido() {
        return atual != null;
    }

    // Avança o ponteiro para o próximo nó
    public void avancar() {
        if (isValido()) {
            atual = atual.getProximo();
        }
    }
}
