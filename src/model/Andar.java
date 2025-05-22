package model;

import utils.FilaDePrioridade;

// Classe que representa um andar do prédio no sistema de elevadores.
// Cada andar possui um número identificador, uma fila de pessoas esperando o elevador
// e um painel que permite chamar o elevador.
public class Andar {

    // Número do andar (ex.: 0 para térreo, 1 para primeiro andar, etc.)
    private int numero;

    // Fila de pessoas esperando o elevador neste andar.
    // Utiliza uma estrutura que prioriza pessoas com mobilidade reduzida.
    private FilaDePrioridade fila;

    // Painel associado ao andar, utilizado para chamar o elevador.
    private PainelElevador painel;

    // Construtor que recebe o número do andar e inicializa a fila e o painel.
    public Andar(int numero) {
        this.numero = numero;
        this.fila = new FilaDePrioridade();  // Inicializa a fila de pessoas
        this.painel = new PainelElevador();  // Inicializa o painel de chamada do elevador
    }

    // Adiciona uma pessoa à fila de espera do elevador neste andar.
    public void adicionarPessoa(Pessoa pessoa) {
        fila.enfileirar(pessoa);  // Adiciona a pessoa na fila com base na prioridade
    }

    // Retorna a próxima pessoa a ser atendida (removida da fila).
    public Pessoa proximaPessoa() {
        return fila.desenfileirar();  // Remove a pessoa da fila
    }

    // Verifica se ainda há pessoas aguardando o elevador neste andar.
    public boolean possuiFila() {
        return !fila.estaVazia();  // Retorna true se a fila não estiver vazia
    }

    // Retorna o número identificador do andar.
    public int getNumero() {
        return numero;
    }

    // Retorna o painel de chamada do elevador deste andar.
    public PainelElevador getPainel() {
        return painel;
    }

    // Retorna o número de pessoas que ainda estão na fila aguardando.
    public int tamanhoFila() {
        return fila.tamanho();  // Quantidade atual de pessoas na fila
    }
}