package control;

import model.Elevador;
import utils.FilaDePrioridade;
import utils.UnidadeDeEnergia; // Import ajustado

import javax.swing.*;
import java.awt.*;

public class ElevadorInterface extends JFrame {
    private CentralDeControle central;
    private int tempoSimulado;
    private JLabel timeLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JPanel elevatorPanel;
    private Timer timer;
    private int currentSecond = 0;
    private boolean isPaused = false;

    public ElevadorInterface(CentralDeControle central, int tempoSimulado) {
        if (central == null) {
            throw new IllegalArgumentException("Central de controle não pode ser nula.");
        }
        if (tempoSimulado <= 0) {
            throw new IllegalArgumentException("Tempo de simulação deve ser maior que 0.");
        }

        this.central = central;
        this.tempoSimulado = tempoSimulado;
        setTitle("Simulador de Elevadores");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        timeLabel = new JLabel("Segundo: 0", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(timeLabel, BorderLayout.NORTH);

        elevatorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Elevador[] elevadores = central.getElevadores();
                FilaDePrioridade[] filasPorAndar = central.getFilasPorAndar();
                int totalAndares = central.getTotalAndares();

                if (totalAndares <= 0) {
                    g.drawString("Erro: Número de andares inválido.", 50, 50);
                    return;
                }

                int andarHeight = (getHeight() - 50) / totalAndares;
                int elevWidth = 40;
                int elevSpacing = 60;

                for (int i = 0; i < totalAndares; i++) {
                    int y = (totalAndares - 1 - i) * andarHeight + 20;
                    g.setColor(i % 2 == 0 ? new Color(200, 200, 200) : new Color(230, 230, 230));
                    g.fillRect(0, y, getWidth(), andarHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, y, getWidth(), andarHeight);
                    g.setFont(new Font("Arial", Font.PLAIN, 14));
                    g.drawString("Andar " + i, 10, y + andarHeight / 2);

                    int pessoasNaFila = (filasPorAndar[i] != null) ? filasPorAndar[i].tamanho() : 0;
                    if (pessoasNaFila > 0) {
                        g.setColor(Color.RED);
                        g.drawString(pessoasNaFila + " pessoa(s)", 100, y + andarHeight / 2);
                    }
                }

                for (int i = 0; i < elevadores.length; i++) {
                    Elevador elev = elevadores[i];
                    if (elev == null) continue;
                    int x = i * elevSpacing + 200;
                    int y = (totalAndares - 1 - elev.getAndarAtual()) * andarHeight + 20;
                    g.setColor(new Color(50, 150, 255));
                    g.fillRoundRect(x, y, elevWidth, andarHeight - 5, 10, 10);
                    g.setColor(Color.BLACK);
                    g.drawRoundRect(x, y, elevWidth, andarHeight - 5, 10, 10);
                    g.setFont(new Font("Arial", Font.BOLD, 12));
                    g.drawString("Elev " + i + " (" + elev.getPessoas().tamanho() + ")", x, y + andarHeight + 15);
                }
            }
        };
        elevatorPanel.setBackground(Color.WHITE);
        add(elevatorPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        startButton = new JButton("Iniciar");
        pauseButton = new JButton("Pausar");
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        pauseButton.setFont(new Font("Arial", Font.PLAIN, 14));
        pauseButton.setEnabled(false);

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            if (!isPaused) {
                currentSecond = 0;
                timer = new Timer(500, e1 -> {
                    currentSecond++;
                    central.atualizar(currentSecond);
                    timeLabel.setText("Segundo: " + currentSecond);
                    elevatorPanel.repaint();

                    if (central.todosElevadoresVazios() && central.todasPessoasProcessadas()) {
                        timer.stop();
                        startButton.setEnabled(true);
                        pauseButton.setEnabled(false);
                        pauseButton.setText("Pausar");

                        System.out.println("\n=== Relatório Final da Simulação ===");
                        System.out.println("Data e hora de término: " +
                                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) +
                                " (Horário de Brasília -03)");
                        System.out.println("Configuração: " + central.getElevadores().length + " elevadores, " +
                                central.getTotalAndares() + " andares, " + tempoSimulado + " segundos.");

                        System.out.println("Total de pessoas transportadas: " + central.getTotalPessoasTransportadas());
                        System.out.println("Pessoas prioritárias atendidas (idosos ou cadeirantes): " +
                                central.getTotalPessoasPrioritariasAtendidas());

                        int pessoasRestantes = 0;
                        for (FilaDePrioridade fila : central.getFilasPorAndar()) {
                            pessoasRestantes += (fila != null) ? fila.tamanho() : 0;
                        }
                        System.out.println("Pessoas ainda esperando nas filas: " + pessoasRestantes);

                        System.out.println("Energia total consumida: " + central.getUnidadeEnergia().getTotalEnergia() + " unidades.");
                        System.out.println("Tempo total de operação: " + central.getUnidadeEnergia().getTempoTotal() + " segundos.");
                        System.out.println("Consumo médio por segundo: " + central.getUnidadeEnergia().calcularConsumoPorTempo() + " unidades/segundo.");

                        System.out.println("===================================");
                    }
                });
                timer.start();
            } else {
                timer.start();
                isPaused = false;
            }
        });

        pauseButton.addActionListener(e -> {
            if (!isPaused) {
                timer.stop();
                pauseButton.setText("Continuar");
                isPaused = true;
            } else {
                timer.start();
                pauseButton.setText("Pausar");
                isPaused = false;
            }
        });

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void iniciar() {
        setVisible(true);
    }
}