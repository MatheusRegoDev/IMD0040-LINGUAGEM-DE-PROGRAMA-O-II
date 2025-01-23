package Banco.model;

import Banco.util.Utils;

import java.time.LocalDateTime;

public class Movimentacao {
    private String tipo;
    private Double valor;
    private LocalDateTime dataHora;
    private String descricao;

    public Movimentacao(String tipo, Double valor, String descricao) {
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "[" + dataHora + "] " + tipo + ": " + Utils.valorToString(valor) +
                (descricao != null && !descricao.isEmpty() ? " (" + descricao + ")" : "");
    }
}
