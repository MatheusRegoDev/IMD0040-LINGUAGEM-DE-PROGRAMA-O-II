package Banco.model;

import Banco.service.Conta;
import Banco.util.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ContaPoupanca implements Conta {
    private String numeroAgencia;
    private String numeroConta;
    private Pessoa titular;
    private Double saldo;
    private String senha;
    private LocalDate ultimaDataRendimento;
    private static final double TAXA_RENDIMENTO = 0.5;
    private ArrayList<Movimentacao> historico;

    public ContaPoupanca(String numeroAgencia, Pessoa titular, String senha) {
        this.numeroAgencia = numeroAgencia;
        this.numeroConta = Utils.gerarNumeroConta();
        this.titular = titular;
        this.saldo = 0.00;
        this.senha = senha;
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

    public void setHistorico(ArrayList<Movimentacao> historico) {
        this.historico = historico;
    }

    public LocalDate getUltimaDataRendimento() {
        return ultimaDataRendimento;
    }

    public void setUltimaDataRendimento(LocalDate ultimaDataRendimento) {
        this.ultimaDataRendimento = ultimaDataRendimento;
    }

    @Override
    public String toString() {
        return "ContaPoupanca{" +
                "numeroAgencia='" + numeroAgencia + '\'' +
                ", numeroConta='" + numeroConta + '\'' +
                ", titular=" + titular +
                ", saldo=" + saldo +
                ", senha='" + senha + '\'' +
                ", ultimaDataRendimento=" + ultimaDataRendimento +
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
        aplicarTaxaRendimento();
        if (this.titular.getEmail().equals(email)){
            if (Objects.equals(this.senha, senha)){
                System.out.println("Login efetuado com sucesso!");
                return true;
            }
            else {
                System.out.println("As senhas não se coincidem ");
                return false;
            }
        }

        System.out.println("E-mail não corresponde ao titular da conta!");
        return false;

    }

    public void aplicarTaxaRendimento() {
        LocalDate hoje = LocalDate.now();

        if (this.ultimaDataRendimento != null && this.ultimaDataRendimento.getMonth() == hoje.getMonth()) {
            System.out.println("A taxa de manutenção já foi aplicada este mês.");
            return;
        }


        if (saldo > 0) {
            saldo += (saldo * TAXA_RENDIMENTO);
            ultimaDataRendimento = hoje;
            historico.add(new Movimentacao("Taxa de Manutenção", TAXA_RENDIMENTO, "Cobrança mensal"));
            System.out.println("Taxa de manutenção aplicada com sucesso.");
        } else {
            System.out.println("Saldo insuficiente para cobrar a taxa de manutenção.");
        }
    }

    @Override
    public ArrayList<Movimentacao> getExtrato(){
        return this.historico;
    }

    @Override
    public void exibirExtrato() {
        for (Movimentacao movimento : getExtrato()){
            System.out.println(movimento);
        }
    }
}

