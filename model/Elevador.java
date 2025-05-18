package model;

import utils.Lista;

// Classe que representa um elevador no prédio
public class Elevador {
    private int andarAtual;                // Andar atual do elevador
    private Lista pessoasNoElevador;      // Lista das pessoas dentro do elevador
    private int capacidade;               // Capacidade máxima do elevador

    // Construtor do elevador, inicia no andar 0 e com capacidade definida
    public Elevador(int capacidade) {
        this.andarAtual = 0;
        this.capacidade = capacidade;
        this.pessoasNoElevador = new Lista();  // Usando lista customizada
    }

    // Retorna o andar atual do elevador
    public int getAndarAtual() {
        return andarAtual;
    }

    // Move o elevador para o próximo andar (simplificação: sobe até último andar e desce)
    public void mover(int totalAndares) {
        // Lógica simples: sobe um andar por ciclo até o último andar, depois desce
        if (andarAtual < totalAndares - 1) {
            andarAtual++;
        } else {
            andarAtual = 0; // Reinicia no térreo após chegar ao topo
        }
    }

    // Adiciona uma pessoa ao elevador se houver capacidade
    public boolean adicionarPessoa(Pessoa pessoa) {
        if (pessoasNoElevador.tamanho() < capacidade) {
            pessoasNoElevador.inserirFim(pessoa);
            return true;
        }
        return false;
    }

    // Remove uma pessoa do elevador
    public void removerPessoa(Pessoa pessoa) {
        // Remove a pessoa da lista iterando e recriando uma nova lista
        Lista novaLista = new Lista();
        for (int i = 0; i < pessoasNoElevador.tamanho(); i++) {
            Pessoa p = (Pessoa) pessoasNoElevador.get(i);
            if (p != pessoa) {
                novaLista.inserirFim(p);
            }
        }
        pessoasNoElevador = novaLista;
    }

    // Retorna a lista das pessoas que devem sair no andar atual
    public Lista pessoasSaindoNoAndar() {
        Lista saindo = new Lista();
        for (int i = 0; i < pessoasNoElevador.tamanho(); i++) {
            Pessoa p = (Pessoa) pessoasNoElevador.get(i);
            if (p.getAndarDestino() == andarAtual) {
                saindo.inserirFim(p);
            }
        }
        return saindo;
    }

    // Retorna todas as pessoas dentro do elevador
    public Lista getPessoas() {
        return pessoasNoElevador;
    }
}
