package model;
import control.CentralDeControle;
import utils.Lista;
import utils.UnidadeDeEnergia;

import java.io.Serializable;

public class Predio implements Serializable {
    // Instância da CentralDeControle, responsável por controlar os elevadores
    private CentralDeControle central;

    // Lista personalizada que armazena os andares do prédio
    private Lista andares;

    // Construtor da classe Predio
    public Predio(int quantidadeAndares, int quantidadeElevadores) {
        andares = new Lista();  // Inicializa a lista de andares

        // Criação dos andares e adição à lista
        for (int i = 0; i < quantidadeAndares; i++) {
            andares.inserirFim(new Andar(i));  // Cada andar recebe um ID (índice)
        }

        // Criação da instância de UnidadeDeEnergia para monitorar consumo
        UnidadeDeEnergia unidadeEnergia = new UnidadeDeEnergia();

        // Criação da CentralDeControle
        central = new CentralDeControle(quantidadeElevadores, quantidadeAndares, unidadeEnergia);
    }

    // Retorna a instância da CentralDeControle
    public CentralDeControle getCentral() {
        return central;
    }

    // Retorna a lista de andares do prédio
    public Lista getAndares() {
        return andares;
    }
}
