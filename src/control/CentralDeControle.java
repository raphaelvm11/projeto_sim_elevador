package control;

import model.Elevador;
import model.Pessoa;
import utils.FilaDePrioridade;
import utils.UnidadeDeEnergia;

import java.util.Random;

public class CentralDeControle {
    private Elevador[] elevadores;
    private FilaDePrioridade[] filasPorAndar;
    private int[] tempoEsperaPorAndar;
    private UnidadeDeEnergia unidadeEnergia;
    private ConfiguracaoSistema config;
    private int capacidadeElevador = 5;
    private int totalPessoasGeradas = 0;
    private int totalPessoasTransportadas = 0;
    private int totalPessoasPrioritariasAtendidas = 0;
    private int maxPessoas = 20;
    private Random random;
    private static final int LIMITE_ESPERA = 5;
    private boolean[][] andaresAlocados;

    public CentralDeControle(ConfiguracaoSistema config, UnidadeDeEnergia unidadeEnergia) {
        if (config == null) {
            throw new IllegalArgumentException("Configuração do sistema não pode ser nula.");
        }
        if (unidadeEnergia == null) {
            throw new IllegalArgumentException("Unidade de energia não pode ser nula.");
        }
        if (capacidadeElevador <= 0) {
            throw new IllegalArgumentException("Capacidade do elevador deve ser maior que 0.");
        }

        this.config = config;
        this.unidadeEnergia = unidadeEnergia;
        elevadores = new Elevador[config.getTotalElevadores()];
        filasPorAndar = new FilaDePrioridade[config.getTotalAndares()];
        tempoEsperaPorAndar = new int[config.getTotalAndares()];
        andaresAlocados = new boolean[config.getTotalElevadores()][config.getTotalAndares()];
        random = new Random();

        for (int i = 0; i < config.getTotalElevadores(); i++) {
            elevadores[i] = new Elevador(capacidadeElevador, config);
        }

        for (int i = 0; i < config.getTotalAndares(); i++) {
            filasPorAndar[i] = new FilaDePrioridade();
        }
    }

    // Construtor temporário para depuração (remover após identificar o problema)
    @Deprecated
    public CentralDeControle(int quantidadeElevadores, int totalAndares, UnidadeDeEnergia unidadeEnergia) {
        System.out.println("Aviso: Usando construtor desatualizado de CentralDeControle. Atualize para usar ConfiguracaoSistema.");
        ConfiguracaoSistema config = new ConfiguracaoSistema(totalAndares, quantidadeElevadores,
                ConfiguracaoSistema.TipoPainelExterno.NUMERICO, false);
        this.config = config;
        this.unidadeEnergia = unidadeEnergia;
        elevadores = new Elevador[quantidadeElevadores];
        filasPorAndar = new FilaDePrioridade[totalAndares];
        tempoEsperaPorAndar = new int[totalAndares];
        andaresAlocados = new boolean[quantidadeElevadores][totalAndares];
        random = new Random();

        for (int i = 0; i < quantidadeElevadores; i++) {
            elevadores[i] = new Elevador(capacidadeElevador, config);
        }

        for (int i = 0; i < totalAndares; i++) {
            filasPorAndar[i] = new FilaDePrioridade();
        }
    }

    private void gerarPessoasAleatorias() {
        if (totalPessoasGeradas >= maxPessoas) return;

        int pessoasAGerar = random.nextInt(2);
        for (int i = 0; i < pessoasAGerar && totalPessoasGeradas < maxPessoas; i++) {
            int idade = random.nextInt(90) + 1;
            boolean cadeirante = random.nextBoolean();
            int andarOrigem = random.nextInt(config.getTotalAndares());
            int andarDestino;
            do {
                andarDestino = random.nextInt(config.getTotalAndares());
            } while (andarDestino == andarOrigem);

            Pessoa novaPessoa = new Pessoa(idade, cadeirante, andarOrigem, andarDestino);
            filasPorAndar[andarOrigem].enfileirar(novaPessoa);
            totalPessoasGeradas++;

            System.out.println("Nova pessoa gerada: Idade = " + idade + ", Cadeirante = " +
                    (cadeirante ? "Sim" : "Não") + ", Andar origem: " + andarOrigem +
                    ", Andar destino: " + andarDestino);
        }
    }

    public void atualizar(int tempoAtual) {
        System.out.println("\n--- Ciclo de simulação: segundo " + tempoAtual + " ---");

        gerarPessoasAleatorias();

        boolean haPessoasNasFilas = false;
        for (int i = 0; i < config.getTotalAndares(); i++) {
            if (filasPorAndar[i] != null && !filasPorAndar[i].estaVazia()) {
                haPessoasNasFilas = true;
                tempoEsperaPorAndar[i]++;
            } else {
                tempoEsperaPorAndar[i] = 0;
                for (int e = 0; e < config.getTotalElevadores(); e++) {
                    andaresAlocados[e][i] = false;
                }
            }
        }

        for (int i = 0; i < config.getTotalElevadores(); i++) {
            Elevador elev = elevadores[i];
            if (elev == null) {
                System.err.println("Erro: Elevador " + i + " é nulo.");
                continue;
            }
            int andarAtual = elev.getAndarAtual();
            boolean precisaMover = false;
            int andarAlvo = -1;

            FilaDePrioridade saindo = elev.pessoasSaindoNoAndar();
            if (saindo != null && !saindo.estaVazia()) {
                while (!saindo.estaVazia()) {
                    Pessoa p = saindo.desenfileirar();
                    if (p == null) continue;
                    System.out.println("Elevador " + i + ": Pessoa saindo -> Idade: " + p.getIdade() +
                            ", Cadeirante: " + (p.isCadeirante() ? "Sim" : "Não") +
                            ", Prioridade: " + (p.temPrioridade() ? "Sim" : "Não") +
                            ", Andar origem: " + p.getAndarOrigem() +
                            ", Andar destino: " + elev.getAndarAtual());
                    totalPessoasTransportadas++;
                    if (p.temPrioridade()) {
                        totalPessoasPrioritariasAtendidas++;
                    }
                }
            }

            if (elev.getPessoas().tamanho() > 0) {
                andarAlvo = DestinoPrioritario.calcularAndarAlvo(elev);
                if (andarAlvo != -1) {
                    precisaMover = true;
                }
            }

            if (andarAlvo == -1 && haPessoasNasFilas) {
                int maiorPrioridade = Integer.MIN_VALUE;
                for (int j = 0; j < config.getTotalAndares(); j++) {
                    if (filasPorAndar[j] != null && !filasPorAndar[j].estaVazia()) {
                        int prioridade = filasPorAndar[j].tamanho() * 10 + tempoEsperaPorAndar[j];
                        if (filasPorAndar[j].temPessoaPrioritaria()) {
                            prioridade += 50;
                        }
                        if (prioridade > maiorPrioridade) {
                            maiorPrioridade = prioridade;
                            andarAlvo = j;
                        }
                    }
                }
                if (andarAlvo != -1) {
                    precisaMover = true;
                }
            }

            if (precisaMover) {
                int distancia = Math.abs(andarAlvo - andarAtual);
                double fatorTempo = config.getFatorTempoViagem();
                int tempoViagem = (int) (distancia * fatorTempo);
                if (tempoAtual % tempoViagem == 0) {
                    int andarAnterior = elev.getAndarAtual();
                    if (andarAlvo != -1) {
                        if (andarAlvo > elev.getAndarAtual() && !elev.isSubindo()) {
                            elev.setSubindo(true);
                        } else if (andarAlvo < elev.getAndarAtual() && elev.isSubindo()) {
                            elev.setSubindo(false);
                        }
                        elev.mover(config.getTotalAndares());
                    }

                    if (elev.getAndarAtual() != andarAnterior) {
                        unidadeEnergia.adicionarConsumo(1, 1);
                    }

                    FilaDePrioridade filaAtual = filasPorAndar[elev.getAndarAtual()];
                    FilaDePrioridade restantes = new FilaDePrioridade();

                    while (filaAtual != null && !filaAtual.estaVazia()) {
                        Pessoa p = filaAtual.desenfileirar();
                        if (p == null) continue;
                        boolean entrou = elev.adicionarPessoa(p);
                        if (entrou) {
                            System.out.println("Andar " + elev.getAndarAtual() + ": Pessoa entrando no elevador -> Idade: " +
                                    p.getIdade() + ", Cadeirante: " + (p.isCadeirante() ? "Sim" : "Não") +
                                    ", Prioridade: " + (p.temPrioridade() ? "Sim" : "Não"));
                        } else {
                            restantes.enfileirar(p);
                        }
                    }

                    filasPorAndar[elev.getAndarAtual()] = restantes;
                    tempoEsperaPorAndar[elev.getAndarAtual()] = 0;
                    andaresAlocados[i][elev.getAndarAtual()] = false;
                }
            }
        }

        if (haPessoasNasFilas) {
            System.out.println("Atenção: Ainda há pessoas nas filas no segundo " + tempoAtual + ".");
        }
    }

    public Elevador[] getElevadores() {
        return elevadores;
    }

    public FilaDePrioridade[] getFilasPorAndar() {
        return filasPorAndar;
    }

    public int getTotalAndares() {
        return config.getTotalAndares();
    }

    public int getTotalPessoasTransportadas() {
        return totalPessoasTransportadas;
    }

    public int getTotalPessoasPrioritariasAtendidas() {
        return totalPessoasPrioritariasAtendidas;
    }

    public UnidadeDeEnergia getUnidadeEnergia() {
        return unidadeEnergia;
    }

    public boolean haPessoasNasFilas() {
        for (int i = 0; i < config.getTotalAndares(); i++) {
            if (filasPorAndar[i] != null && !filasPorAndar[i].estaVazia()) {
                return true;
            }
        }
        return false;
    }

    public boolean todosElevadoresVazios() {
        for (Elevador elev : elevadores) {
            if (elev == null || elev.getPessoas().tamanho() > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean todasPessoasProcessadas() {
        return totalPessoasGeradas >= maxPessoas && !haPessoasNasFilas();
    }

    public int getTotalPessoasGeradas() {
        return totalPessoasGeradas;
    }

    public int getMaxPessoas() {
        return maxPessoas;
    }
}