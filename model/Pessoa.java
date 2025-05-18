package model;

// Classe que representa uma pessoa no prédio
public class Pessoa {
    private int idade;          // Idade da pessoa
    private boolean cadeirante; // Indica se a pessoa é cadeirante
    private int andarDestino;   // Andar para o qual a pessoa deseja ir

    // Construtor que inicializa idade, cadeirante e andar destino
    public Pessoa(int idade, boolean cadeirante, int andarDestino) {
        this.idade = idade;
        this.cadeirante = cadeirante;
        this.andarDestino = andarDestino;
    }

    // Retorna a idade da pessoa
    public int getIdade() {
        return idade;
    }

    // Retorna se a pessoa é cadeirante
    public boolean isCadeirante() {
        return cadeirante;
    }

    // Retorna o andar destino da pessoa
    public int getAndarDestino() {
        return andarDestino;
    }

    // Indica se a pessoa tem prioridade (idade >= 60 ou cadeirante)
    public boolean temPrioridade() {
        return (idade >= 60) || cadeirante;
    }
}
