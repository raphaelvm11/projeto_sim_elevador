package utils;

import model.Pessoa;

public class FilaDePrioridade {
    private Pessoa[] heap;
    private int tamanho;
    private static final int CAPACIDADE_INICIAL = 10;

    public FilaDePrioridade() {
        this.heap = new Pessoa[CAPACIDADE_INICIAL];
        this.tamanho = 0;
    }

    public void enfileirar(Pessoa pessoa) {
        if (pessoa == null) return;
        if (tamanho == heap.length) {
            resize();
        }
        heap[tamanho] = pessoa;
        int i = tamanho;
        while (i > 0) {
            int pai = (i - 1) / 2;
            if (heap[pai] == null || heap[i] == null || heap[pai].temPrioridade() && !heap[i].temPrioridade()) {
                break;
            }
            if (!heap[pai].temPrioridade() && heap[i].temPrioridade()) {
                Pessoa temp = heap[pai];
                heap[pai] = heap[i];
                heap[i] = temp;
            }
            i = pai;
        }
        tamanho++;
    }

    public Pessoa desenfileirar() {
        if (tamanho == 0) return null;
        Pessoa resultado = heap[0];
        heap[0] = heap[tamanho - 1];
        heap[tamanho - 1] = null;
        tamanho--;
        int i = 0;
        while (true) {
            int filhoEsq = 2 * i + 1;
            int filhoDir = 2 * i + 2;
            int maior = i;
            if (filhoEsq < tamanho && heap[filhoEsq] != null &&
                    ((heap[maior] == null || !heap[maior].temPrioridade() && heap[filhoEsq].temPrioridade()))) {
                maior = filhoEsq;
            }
            if (filhoDir < tamanho && heap[filhoDir] != null &&
                    ((heap[maior] == null || !heap[maior].temPrioridade() && heap[filhoDir].temPrioridade()))) {
                maior = filhoDir;
            }
            if (maior == i) break;
            Pessoa temp = heap[i];
            heap[i] = heap[maior];
            heap[maior] = temp;
            i = maior;
        }
        return resultado;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    private void resize() {
        Pessoa[] novoHeap = new Pessoa[heap.length * 2];
        System.arraycopy(heap, 0, novoHeap, 0, heap.length);
        heap = novoHeap;
    }

    public boolean temPessoaPrioritaria() {
        // Cria uma cópia temporária para não modificar a fila original
        FilaDePrioridade temp = new FilaDePrioridade();
        boolean temPrioridade = false;

        // Copia todas as pessoas para a fila temporária
        for (int i = 0; i < tamanho; i++) {
            if (heap[i] != null) {
                temp.enfileirar(heap[i]);
            }
        }

        // Verifica cada pessoa na fila temporária
        while (!temp.estaVazia()) {
            Pessoa p = temp.desenfileirar();
            if (p != null && p.temPrioridade()) {
                temPrioridade = true;
                break;
            }
        }

        return temPrioridade;
    }
}