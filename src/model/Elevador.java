package model;

import control.ConfiguracaoSistema;
import utils.FilaDePrioridade;

public class Elevador {
    private int andarAtual;
    private FilaDePrioridade pessoasNoElevador;
    private int capacidade;
    private boolean subindo = true;
    private ConfiguracaoSistema config;

    public Elevador(int capacidade, ConfiguracaoSistema config) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade do elevador deve ser maior que 0.");
        }
        if (config == null) {
            throw new IllegalArgumentException("Configuração do sistema não pode ser nula.");
        }
        this.andarAtual = 0;
        this.capacidade = capacidade;
        this.pessoasNoElevador = new FilaDePrioridade();
        this.config = config;
    }

    public int getAndarAtual() {
        return andarAtual;
    }

    public void setSubindo(boolean subindo) {
        this.subindo = subindo;
    }

    public boolean isSubindo() {
        return subindo;
    }

    public void mover(int totalAndares) {
        if (totalAndares <= 0) {
            throw new IllegalArgumentException("Total de andares deve ser maior que 0.");
        }
        if (subindo) {
            if (andarAtual < totalAndares - 1) {
                andarAtual++;
            } else {
                subindo = false;
            }
        } else {
            if (andarAtual > 0) {
                andarAtual--;
            } else {
                subindo = true;
            }
        }
    }

    public boolean adicionarPessoa(Pessoa pessoa) {
        if (pessoa == null) return false;
        if (pessoasNoElevador.tamanho() < capacidade) {
            pessoasNoElevador.enfileirar(pessoa);
            return true;
        }
        return false;
    }

    public void removerPessoa(Pessoa pessoa) {
        if (pessoa == null) return;
        FilaDePrioridade novaLista = new FilaDePrioridade();
        while (!pessoasNoElevador.estaVazia()) {
            Pessoa p = pessoasNoElevador.desenfileirar();
            if (!p.equals(pessoa)) {
                novaLista.enfileirar(p);
            }
        }
        pessoasNoElevador = novaLista;
    }

    public FilaDePrioridade pessoasSaindoNoAndar() {
        FilaDePrioridade saindo = new FilaDePrioridade();
        FilaDePrioridade temp = new FilaDePrioridade();

        while (!pessoasNoElevador.estaVazia()) {
            Pessoa p = pessoasNoElevador.desenfileirar();
            if (p.getAndarDestino() == andarAtual) {
                saindo.enfileirar(p);
            } else {
                temp.enfileirar(p);
            }
        }

        pessoasNoElevador = temp;
        return saindo;
    }

    public FilaDePrioridade getPessoas() {
        return pessoasNoElevador;
    }

    public String getPainelInternoDescricao() {
        switch (config.getTipoPainelExterno()) {
            case CHAMADA_GERAL:
                return "Painel interno: Botão de destino geral.";
            case SUBIR_DESCER:
                return "Painel interno: Botões de subir e descer.";
            case NUMERICO:
                return "Painel interno: Digite o andar desejado (0-" + (config.getTotalAndares() - 1) + ").";
            default:
                return "Painel interno: Não configurado.";
        }
    }
}