package utils;

// Classe que gerencia o consumo de energia durante a simulação
public class UnidadeDeEnergia {
    private double totalEnergia;  // Total de energia consumida
    private int tempoTotal;       // Tempo total de operação em segundos

    // Construtor que inicializa os valores
    public UnidadeDeEnergia() {
        this.totalEnergia = 0.0;
        this.tempoTotal = 0;
    }

    // Adiciona consumo de energia e incrementa o tempo
    public void adicionarConsumo(double energia, int tempo) {
        totalEnergia += energia;
        tempoTotal += tempo;
    }

    // Retorna o total de energia consumida
    public double getTotalEnergia() {
        return totalEnergia;
    }

    // Retorna o tempo total de operação
    public int getTempoTotal() {
        return tempoTotal;
    }

    // Calcula o consumo médio por segundo
    public double calcularConsumoPorTempo() {
        return tempoTotal > 0 ? totalEnergia / tempoTotal : 0.0;
    }
}
