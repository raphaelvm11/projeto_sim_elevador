package control;

import model.Elevador;
import model.Pessoa;
import utils.FilaDePrioridade;

public class DestinoPrioritario {
    public static int calcularAndarAlvo(Elevador elevador) {
        if (elevador == null || elevador.getPessoas().tamanho() == 0) {
            return -1; // Nenhum destino prioritário se o elevador estiver vazio
        }

        FilaDePrioridade pessoas = elevador.getPessoas();
        FilaDePrioridade temp = new FilaDePrioridade();
        int menorDistancia = Integer.MAX_VALUE;
        int andarAlvo = -1;
        int andarAtual = elevador.getAndarAtual();

        // Percorre as pessoas no elevador para encontrar o destino mais próximo
        while (!pessoas.estaVazia()) {
            Pessoa p = pessoas.desenfileirar();
            int destino = p.getAndarDestino();
            int distancia = Math.abs(destino - andarAtual);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                andarAlvo = destino;
            }
            temp.enfileirar(p);
        }

        // Restaura a fila de pessoas
        while (!temp.estaVazia()) {
            pessoas.enfileirar(temp.desenfileirar());
        }

        return andarAlvo;
    }
}
