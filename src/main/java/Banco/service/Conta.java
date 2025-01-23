package Banco.service;

import Banco.model.Movimentacao;

import java.util.ArrayList;

public interface Conta {
    public void depositar(Double valor);
    public void sacar(Double valor);
    public void tranferir(Conta contaParaTranferir, double valor);
    public boolean autenticacao(String email, String senha);
    ArrayList<Movimentacao> getExtrato();
    public void exibirExtrato();
}
