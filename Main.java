import control.Simulador;

// Classe principal que inicia e gerencia a execução da simulação de elevadores
public class Main {
    public static void main(String[] args) {
        // Valores padrão ajustados para a nova configuração
        int quantidadeElevadores = 5;  //  5 elevadores
        int quantidadeAndares = 20;    //  20 andares
        int tempoSimulado = 40;        //  40 segundos

        // Tenta obter parâmetros de linha de comando, se disponíveis
        try {
            if (args.length >= 3) {
                quantidadeElevadores = Integer.parseInt(args[0]);
                quantidadeAndares = Integer.parseInt(args[1]);
                tempoSimulado = Integer.parseInt(args[2]);
            } else if (args.length > 0) {
                System.out.println("Aviso: Forneça todos os 3 parâmetros (elevadores, andares, tempo) ou nenhum.");
            }

            // Validações dos parâmetros
            if (quantidadeElevadores <= 0) {
                throw new IllegalArgumentException("A quantidade de elevadores deve ser maior que 0.");
            }
            if (quantidadeAndares <= 0) {
                throw new IllegalArgumentException("A quantidade de andares deve ser maior que 0.");
            }
            if (tempoSimulado <= 0) {
                throw new IllegalArgumentException("O tempo de simulação deve ser maior que 0.");
            }
            if (quantidadeElevadores > 10) {
                System.out.println("Aviso: Quantidade de elevadores limitada a 10 por desempenho. Ajustando para 10.");
                quantidadeElevadores = 10;
            }
            if (quantidadeAndares > 50) {
                System.out.println("Aviso: Quantidade de andares limitada a 50. Ajustando para 50.");
                quantidadeAndares = 50;
            }

            // Exibe informações de início com data e hora atual
            java.time.LocalDateTime agora = java.time.LocalDateTime.now();
            System.out.println("Simulação iniciada em " + agora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                    " (Horário de Brasília -03).");
            System.out.println("Configuração: " + quantidadeElevadores + " elevadores, " + quantidadeAndares +
                    " andares, " + tempoSimulado + " segundos.");

            // Cria e inicia a simulação
            Simulador simulador = new Simulador(quantidadeElevadores, quantidadeAndares, tempoSimulado);
            simulador.iniciar();

            // Exibe mensagem de conclusão
            System.out.println("\nSimulação finalizada com sucesso às " +
                    java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) + ".");

        } catch (NumberFormatException e) {
            System.err.println("Erro: Parâmetros inválidos. Use números inteiros para elevadores, andares e tempo.");
            System.err.println("Exemplo: java Main 5 20 40");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado durante a simulação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}