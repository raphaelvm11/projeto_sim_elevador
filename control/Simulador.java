package control;

import utils.UnidadeDeEnergia;

public class Simulador {
    private CentralDeControle central;      // Controla os elevadores e andares
    private int tempoSimulado;              // Duração da simulação em segundos
    private UnidadeDeEnergia unidadeEnergia; // Mede o consumo de energia

    // Construtor para inicializar o simulador
    public Simulador(int quantidadeElevadores, int quantidadeAndares, int tempoSimulado) {
        this.unidadeEnergia = new UnidadeDeEnergia();
        this.central = new CentralDeControle(quantidadeElevadores, quantidadeAndares, unidadeEnergia);
        this.tempoSimulado = tempoSimulado;
    }

    // Inicia e executa a simulação
    public void iniciar() {
        for (int segundo = 1; segundo <= tempoSimulado; segundo++) {
            System.out.println("\n--- Ciclo de simulação: segundo " + segundo + " ---");

            // Atualiza a lógica da central de controle
            central.atualizar(segundo);

            // Pausa de 1 segundo para simular tempo real (ajustável para testes)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Exibe resultados após a simulação
        System.out.println("\nSimulação concluída.");
        System.out.println("Energia total consumida: " + unidadeEnergia.getTotalEnergia() + " unidades.");
        System.out.println("Tempo total de operação: " + unidadeEnergia.getTempoTotal() + " segundos.");
        System.out.println("Consumo médio por segundo: " + unidadeEnergia.calcularConsumoPorTempo() + " unidades/segundo.");
    }
}
