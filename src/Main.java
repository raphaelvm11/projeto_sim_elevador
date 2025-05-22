import control.ConfiguracaoSistema;
import control.CentralDeControle;
import control.ElevadorInterface;
import utils.UnidadeDeEnergia; // Import ajustado
import control.DestinoPrioritario;

public class Main {
    public static void main(String[] args) {
        int quantidadeElevadores = 2;
        int quantidadeAndares = 9;
        int tempoSimulado = 40;

        try {
            if (args.length >= 3) {
                quantidadeElevadores = Integer.parseInt(args[0]);
                quantidadeAndares = Integer.parseInt(args[1]);
                tempoSimulado = Integer.parseInt(args[2]);
            } else if (args.length > 0) {
                System.out.println("Aviso: Forneça todos os 3 parâmetros (elevadores, andares, tempo) ou nenhum.");
            }

            if (quantidadeElevadores <= 0) {
                throw new IllegalArgumentException("A quantidade de elevadores deve ser maior que 0.");
            }
            if (quantidadeAndares < 5 || quantidadeAndares > 50) {
                throw new IllegalArgumentException("A quantidade de andares deve estar entre 5 e 50.");
            }
            if (tempoSimulado <= 0) {
                throw new IllegalArgumentException("O tempo de simulação deve ser maior que 0.");
            }
            if (quantidadeElevadores > 10) {
                System.out.println("Aviso: Quantidade de elevadores limitada a 10 por desempenho. Ajustando para 10.");
                quantidadeElevadores = 10;
            }

            java.time.LocalDateTime agora = java.time.LocalDateTime.now();
            System.out.println("Simulação iniciada em " + agora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                    " (Horário de Brasília -03).");
            System.out.println("Configuração: " + quantidadeElevadores + " elevadores, " + quantidadeAndares +
                    " andares, " + tempoSimulado + " segundos.");

            ConfiguracaoSistema config = new ConfiguracaoSistema(quantidadeAndares, quantidadeElevadores,
                    ConfiguracaoSistema.TipoPainelExterno.NUMERICO, false);
            CentralDeControle central = new CentralDeControle(config, new UnidadeDeEnergia());
            ElevadorInterface interfaceSimulacao = new ElevadorInterface(central, tempoSimulado);
            interfaceSimulacao.iniciar();

            System.out.println("\nSimulação finalizada com sucesso às " +
                    java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) + ".");

        } catch (NumberFormatException e) {
            System.err.println("Erro: Parâmetros inválidos. Use números inteiros para elevadores, andares e tempo.");
            System.err.println("Exemplo: java Main 2 9 40");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado durante a simulação: " + e.getMessage());
        }
    }
}