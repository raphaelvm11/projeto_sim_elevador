package model;

public class Pessoa {
    private int idade;
    private boolean cadeirante;
    private int andarOrigem;
    private int andarDestino;

    public Pessoa(int idade, boolean cadeirante, int andarOrigem, int andarDestino) {
        this.idade = idade;
        this.cadeirante = cadeirante;
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
    }

    public int getIdade() {
        return idade;
    }

    public boolean isCadeirante() {
        return cadeirante;
    }

    public int getAndarOrigem() {
        return andarOrigem;
    }

    public int getAndarDestino() {
        return andarDestino;
    }

    public boolean temPrioridade() {
        return idade > 60 || cadeirante;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pessoa pessoa = (Pessoa) obj;
        return idade == pessoa.idade &&
                cadeirante == pessoa.cadeirante &&
                andarOrigem == pessoa.andarOrigem &&
                andarDestino == pessoa.andarDestino;
    }
}