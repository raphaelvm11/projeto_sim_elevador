package control;

import utils.UnidadeDeEnergia;
import utils.FilaDePrioridade;

public class Simulador {
    private CentralDeControle central;
    private int tempoSimulado;
    private UnidadeDeEnergia unidadeEnergia;
    private ElevadorInterface interfaceGrafica;

    public Simulador(int quantidadeElevadores, int quantidadeAndares, int tempoSimulado) {
        this.unidadeEnergia = new UnidadeDeEnergia();
        this.central = new CentralDeControle(quantidadeElevadores, quantidadeAndares, unidadeEnergia);
        this.tempoSimulado = tempoSimulado;
        this.interfaceGrafica = new ElevadorInterface(central, tempoSimulado);
    }

    public void iniciar() {
        interfaceGrafica.iniciar();
    }
}