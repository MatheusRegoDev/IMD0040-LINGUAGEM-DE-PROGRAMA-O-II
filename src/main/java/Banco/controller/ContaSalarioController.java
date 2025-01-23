package Banco.controller;

import Banco.dao.ContaSalarioDao;
import Banco.model.ContaSalario;
import Banco.model.Pessoa;
import Banco.util.Utils;

import java.util.ArrayList;
import java.util.Scanner;

public class ContaSalarioController {
    private static Scanner scanner = new Scanner(System.in);

    public static void abrirConta(String numeroAgencia, String cpf, String senha){
        Pessoa pessoaEncontrada = Utils.isPessoaExiste(cpf);
        if ( pessoaEncontrada != null){
            ContaSalario contaSalario = new ContaSalario(numeroAgencia, pessoaEncontrada, senha);
            ContaSalarioDao.salvarConta(contaSalario);
            System.out.println("Sua conta poupança foi aberta com sucesso!");
        }
        else {
            System.out.println("Não foi possível realizar a abertura de conta!");
        }
    }

    public static void deletarConta(String agencia, String numeroConta){
        ArrayList<ContaSalario> listaContaSalario = ContaSalarioDao.lerContas();
        ContaSalario  contaSalarioEncontrada = null;
        for (ContaSalario conta : listaContaSalario){
            if (conta.getNumeroAgencia() == agencia && conta.getNumeroConta() == numeroConta){
                contaSalarioEncontrada = conta;
                break;
            }
        }
        if (contaSalarioEncontrada == null){
            System.out.println("Conta para deletar não existe!");
        }
        else{
            listaContaSalario.remove(contaSalarioEncontrada);
            ContaSalarioDao.reescreverContas(listaContaSalario);
            System.out.println("Conta deletada com sucesso!");
        }

    }

    public static void listarConta(){
        ArrayList<ContaSalario> listaContaSalario = ContaSalarioDao.lerContas();
        for (ContaSalario conta: listaContaSalario){
            System.out.println(conta.toString());
        }
    }

    public static void loginConta(String email, String senha) {
        ContaSalario conta = Utils.isContaSalarioExiste(email);
        if (conta != null) {
            if (conta.autenticacao(email, senha)) {
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
        } else {
            System.out.println("Não foi possível fazer o login");
        }
    }

    private static void processarOperacao(int opcao, ContaSalario conta) {
        ArrayList<ContaSalario> lista = ContaSalarioDao.lerContas();
        switch (opcao){
            case 1:
                double valorDeposito;
                System.out.println("==== DEPÓSITO ====");
                System.out.print("Quantia para depósito: ");
                valorDeposito = scanner.nextDouble();
                scanner.nextLine();
                conta.depositar(valorDeposito);
                ContaSalarioDao.reescreverContas(lista);
                break;

            case 2:
                double valorSaque;
                System.out.println("==== SAQUE ====");
                System.out.print("Quantia para sacar: ");
                valorSaque = scanner.nextDouble();
                scanner.nextLine();
                conta.depositar(valorSaque);
                ContaSalarioDao.reescreverContas(lista);
                break;

            case 3:
            String numeroConta;
            double valorTransferencia;
                System.out.println("==== TRANSFERÊNCIA ====");
                System.out.print("Informe a conta para transferência: ");
                numeroConta = scanner.toString();
                System.out.print("Digite a quantia: ");
                valorTransferencia = scanner.nextDouble();
                ContaSalario contaTransferir = Utils.getContaSalario(numeroConta);
                conta.tranferir(contaTransferir, valorTransferencia);
                ContaSalarioDao.reescreverContas(lista);
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
