package control;

import java.time.LocalTime;

public class ConfiguracaoSistema {
    public enum TipoPainelExterno {
        CHAMADA_GERAL,
        SUBIR_DESCER,
        NUMERICO
    }

    private int minAndares = 5;
    private int maxAndares = 50;
    private int minElevadores = 1;
    private int maxElevadores = 10;
    private int totalAndares;
    private int totalElevadores;
    private TipoPainelExterno tipoPainelExterno;
    private boolean filasSeparadas;

    public ConfiguracaoSistema(int totalAndares, int totalElevadores, TipoPainelExterno tipoPainelExterno, boolean filasSeparadas) {
        if (totalAndares < minAndares || totalAndares > maxAndares) {
            throw new IllegalArgumentException("O número de andares deve estar entre " + minAndares + " e " + maxAndares + ".");
        }
        if (totalElevadores < minElevadores || totalElevadores > maxElevadores) {
            throw new IllegalArgumentException("O número de elevadores deve estar entre " + minElevadores + " e " + maxElevadores + ".");
        }
        this.totalAndares = totalAndares;
        this.totalElevadores = totalElevadores;
        this.tipoPainelExterno = tipoPainelExterno;
        this.filasSeparadas = filasSeparadas;
    }

    public int getTotalAndares() {
        return totalAndares;
    }

    public int getTotalElevadores() {
        return totalElevadores;
    }

    public TipoPainelExterno getTipoPainelExterno() {
        return tipoPainelExterno;
    }

    public boolean isFilasSeparadas() {
        return filasSeparadas;
    }

    public boolean isHorarioPico() {
        LocalTime agora = LocalTime.now();
        return (agora.isAfter(LocalTime.of(8, 0)) && agora.isBefore(LocalTime.of(10, 0))) ||
                (agora.isAfter(LocalTime.of(17, 0)) && agora.isBefore(LocalTime.of(19, 0)));
    }

    public double getFatorTempoViagem() {
        return isHorarioPico() ? 1.5 : 1.0; // 50% mais lento no pico
    }
}