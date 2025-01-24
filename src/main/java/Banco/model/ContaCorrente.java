package Banco.model;

import Banco.service.Conta;
import Banco.util.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ContaCorrente implements Conta {
    private String numeroAgencia;
    private String numeroConta;
    private Pessoa titular;
    private double saldo;
    private String senha;
    private LocalDate ultimaDataManutencao;
    private static final double TAXA_MANUTENCAO = 12.50;
    private ArrayList<Movimentacao> historico;

    public ContaCorrente(String numeroAgencia, Pessoa titular, String senha) {
        this.numeroAgencia = numeroAgencia;
        this.numeroConta = Utils.gerarNumeroConta();
        this.titular = titular;
        this.saldo = 0.00;
        this.senha = senha;
        this.ultimaDataManutencao = LocalDate.now();
        this.historico = new ArrayList<>();
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Pessoa getTitular() {
        return titular;
    }


    public void setTitular(Pessoa titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getUltimaDataManutecao() {
        return ultimaDataManutencao;
    }

    public void setUltimaDataManuntecao(LocalDate ultimaDataManuntecao) {
        this.ultimaDataManutencao = ultimaDataManuntecao;
    }

    public void setHistorico(ArrayList<Movimentacao> historico) {
        this.historico = historico;
    }

    @Override
    public String toString() {
        return "ContaCorrente{" +
                "numeroAgencia='" + numeroAgencia + '\'' +
                ", numeroConta='" + numeroConta + '\'' +
                ", titular=" + titular +
                ", saldo=" + saldo +
                ", senha='" + senha + '\'' +
                ", ultimaDataManuntecao=" + ultimaDataManutencao +
                '}';
    }

    @Override
    public void depositar(double valor) {
        if(valor > 0){
            this.setSaldo(this.getSaldo() + valor);
            this.historico.add(new Movimentacao("Deṕosito", valor, null));
            System.out.println("Deposito realizado com sucesso!");
        }

        else{
            System.out.println("Não foi possível realizar deposito");
        }
    }

    @Override
    public void sacar(double valor) {
        if (valor > 0 && valor <= this.getSaldo()){
            this.setSaldo(this.getSaldo() - valor);
            this.historico.add(new Movimentacao("Saque", valor, null));
            System.out.println("Saque realizado com sucesso!");
        }
        else {
            System.out.println("Não foi possível realizar saque!");
        }
    }

    @Override
    public void tranferir(Conta contaParaTranferir, double valor) {
        if (valor > 0 && valor <= this.getSaldo()){
            this.setSaldo(this.getSaldo() - valor);
            contaParaTranferir.setSaldo(contaParaTranferir.getSaldo() + valor);
            this.historico.add(new Movimentacao("Transferência", valor, "Transferido para outra conta"));
            contaParaTranferir.getExtrato().add(new Movimentacao("Transferencia", valor, "Recebido de outra conta"));
            System.out.println("Transferencia enviada com sucesso!");
        }
        else {
            System.out.println("Não foi possível realizar transferencia!");
        }
    }

    @Override
    public boolean autenticacao(String email, String senha) {
        aplicarTaxaManutencao();
        if (this.titular.getEmail().equals(email)) {
            if (Objects.equals(this.senha, senha)) {
                System.out.println("Login efetuado com sucesso!");
                return true;
            } else {
                System.out.println("As senhas não se coincidem ");
                return false;
            }
        }
        return false;
    }

    public void aplicarTaxaManutencao() {
        LocalDate hoje = LocalDate.now();
        
        if (this.ultimaDataManutencao != null && ultimaDataManutencao.getMonth() == hoje.getMonth()) {
            System.out.println("A taxa de manutenção já foi aplicada este mês.");
            return;
        }


        if (saldo >= TAXA_MANUTENCAO) {
            saldo -= TAXA_MANUTENCAO;
            ultimaDataManutencao = hoje;
            historico.add(new Movimentacao("Taxa de Manutenção", TAXA_MANUTENCAO, "Cobrança mensal"));
            System.out.println("Taxa de manutenção aplicada com sucesso.");
        } else {
            System.out.println("Saldo insuficiente para cobrar a taxa de manutenção.");
        }
    }


    @Override
    public ArrayList<Movimentacao> getExtrato () {
        return this.historico;
    }

    @Override
    public void exibirExtrato () {
        for (Movimentacao movimento : getExtrato()) {
            System.out.println(movimento);
        }
    }
}