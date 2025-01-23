package Banco.controller;

import Banco.dao.ContaCorrenteDao;
import Banco.model.ContaCorrente;
import Banco.model.Pessoa;
import Banco.util.Utils;

import java.util.ArrayList;
import java.util.Scanner;

public class ContaCorrenteController{
    private static Scanner scanner = new Scanner(System.in);

    public static void abrirConta(String numeroAgencia, String cpf, String senha){
        Pessoa pessoaEncontrada = Utils.isPessoaExiste(cpf);
        if ( pessoaEncontrada != null){
            ContaCorrente contaCorrente = new ContaCorrente(numeroAgencia, pessoaEncontrada, senha);
            ContaCorrenteDao.salvarConta(contaCorrente);
            System.out.println("Sua conta poupança foi aberta com sucesso!");
        }
        else {
            System.out.println("Não foi possível realizar a abertura de conta!");
        }
    }

    public static void deletarConta(String agencia, String numeroConta){
        ArrayList<ContaCorrente> listaContaCorrente = ContaCorrenteDao.lerContas();
        ContaCorrente  contaCorrenteEncontrada = null;
        for (ContaCorrente conta : listaContaCorrente){
            if (conta.getNumeroAgencia() == agencia && conta.getNumeroConta() == numeroConta){
                contaCorrenteEncontrada = conta;
                break;
            }
        }
        if (contaCorrenteEncontrada == null){
            System.out.println("Conta para deletar não existe!");
        }
        else{
            listaContaCorrente.remove(contaCorrenteEncontrada);
            ContaCorrenteDao.reescreverContas(listaContaCorrente);
            System.out.println("Conta deletada com sucesso!");
        }

    }

    public static void listarConta(){
        ArrayList<ContaCorrente> listaContaCorrente = ContaCorrenteDao.lerContas();
        for (ContaCorrente conta: listaContaCorrente){
            System.out.println(conta.toString());
        }
    }

    public static void loginConta(String email, String senha){
        ContaCorrente conta = Utils.isContaCorrenteExiste(email);
        if(conta != null){
            if (conta.autenticacao(email, senha)){
                int opcao;
                do {
                    System.out.println("Olá, " + conta.getTitular().getNome() + "!\n" +
                            "Digite o número correspondente a operação:\n" +
                            "1 - Deposito\n" +
                            "2 - Saque\n" +
                            "3 - Transferência\n" +
                            "4 - Extrato\n" +
                            "0 - sair\n");

                    opcao = scanner.nextInt();
                    scanner.nextLine();
                    processarOperacao(opcao, conta);
                }while (opcao != 0);
            }
        }
        else {
            System.out.println("Não foi possível fazer o login");
        }
    }

    private static void processarOperacao(int opcao, ContaCorrente conta) {
        ArrayList<ContaCorrente> lista = ContaCorrenteDao.lerContas();
        switch (opcao){
            case 1:
                double valorDeposito;
                System.out.println("==== DEPÓSITO ====");
                System.out.print("Quantia para depósito: ");
                valorDeposito = scanner.nextDouble();
                scanner.nextLine();
                conta.depositar(valorDeposito);
                ContaCorrenteDao.reescreverContas(lista);
                break;

            case 2:
                double valorSaque;
                System.out.println("==== SAQUE ====");
                System.out.print("Quantia para sacar: ");
                valorSaque = scanner.nextDouble();
                scanner.nextLine();
                conta.depositar(valorSaque);
                ContaCorrenteDao.reescreverContas(lista);
                break;

            case 3:
                String numeroConta;
                double valorTransferencia;
                System.out.println("==== TRANSFERÊNCIA ====");
                System.out.print("Informe a conta para transferência: ");
                numeroConta = scanner.toString();
                System.out.print("Digite a quantia: ");
                valorTransferencia = scanner.nextDouble();
                ContaCorrente contaTransferir = Utils.getContaCorrente(numeroConta);
                conta.tranferir(contaTransferir, valorTransferencia);
                ContaCorrenteDao.reescreverContas(lista);
                break;

            case 4:
                conta.exibirExtrato();

            case 0:
                System.out.println("Saindo...");

            default:
                System.out.println("Opção inválida!");
        }
    }
}
