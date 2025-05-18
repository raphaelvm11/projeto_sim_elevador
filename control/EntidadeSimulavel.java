package control;
import java.io.Serializable;

// Classe abstrata base para entidades simuláveis (elevadores, andares, etc.).
// Define um contrato para o método 'atualizar', chamado a cada unidade de tempo.
public abstract class EntidadeSimulavel implements Serializable {

    // Método abstrato para atualizar o estado da entidade em cada minuto simulado.
    public abstract void atualizar(int minutoSimulado);
}
