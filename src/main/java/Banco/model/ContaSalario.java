package Banco.model;

import Banco.service.Conta;
import Banco.util.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ContaSalario implements Conta {
    private String numeroAgencia;
    private String numeroConta;
    private Pessoa titular;
    private Double saldo;
    private String senha;
    private int saquesRealizadosHoje;
    private LocalDate ultimaDataSaque;
    private static final int LIMITE_SAQUES_DIARIO = 3;
    private ArrayList<Movimentacao> historico;

    public ContaSalario(String numeroAgencia, Pessoa titular, String senha) {
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getSaquesRealizadosHoje() {
        return saquesRealizadosHoje;
    }

    public void setSaquesRealizadosHoje(int saquesRealizadosHoje) {
        this.saquesRealizadosHoje = saquesRealizadosHoje;
    }

    public LocalDate getUltimaDataSaque() {
        return ultimaDataSaque;
    }

    public void setUltimaDataSaque(LocalDate ultimaDataSaque) {
        this.ultimaDataSaque = ultimaDataSaque;
    }

    public void setHistorico(ArrayList<Movimentacao> historico) {
        this.historico = historico;
    }

    @Override
    public String toString() {
        return "ContaSalario{" +
                "numeroAgencia='" + numeroAgencia + '\'' +
                ", numeroConta='" + numeroConta + '\'' +
                ", titular=" + titular +
                ", saldo=" + saldo +
                ", senha='" + senha + '\'' +
                ", saquesRealizadosHoje=" + saquesRealizadosHoje +
                '}';
    }

    public void depositar(Double valor) {
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
    public void sacar(Double valor) {
        LocalDate hoje = LocalDate.now();
        if (this.ultimaDataSaque== null || !this.ultimaDataSaque.equals(hoje)) {
            this.ultimaDataSaque = hoje;
            saquesRealizadosHoje = 0;
        }

        if (saquesRealizadosHoje >= LIMITE_SAQUES_DIARIO) {
            System.out.println("Você atingiu o limite diário de saques.");
            return;
        }

        if (valor > 0 && valor <= this.getSaldo()){
            this.setSaldo(this.getSaldo() - valor);
            saquesRealizadosHoje++;
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
            contaParaTranferir.depositar(valor);
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

    @Override
    public ArrayList<Movimentacao> getExtrato() {
        return this.historico;
    }

    @Override
    public void exibirExtrato() {
        for (Movimentacao movimento : getExtrato()){
            System.out.println(movimento);
        }
    }

    public int saquesRestantesHoje() {
        LocalDate hoje = LocalDate.now();
        if (this.ultimaDataSaque == null || !this.ultimaDataSaque.equals(hoje)) {
            return LIMITE_SAQUES_DIARIO;
        }
        return LIMITE_SAQUES_DIARIO - saquesRealizadosHoje;
    }

}
