package Banco.service;

import Banco.model.Movimentacao;

import java.util.ArrayList;

public interface Conta {
    void depositar(double valor);
    void sacar(double valor);
    void tranferir(Conta contaParaTranferir, double valor);
    boolean autenticacao(String email, String senha);
    ArrayList<Movimentacao> getExtrato();
    void exibirExtrato();
    void setSaldo(double saldo);
    double getSaldo();
}
