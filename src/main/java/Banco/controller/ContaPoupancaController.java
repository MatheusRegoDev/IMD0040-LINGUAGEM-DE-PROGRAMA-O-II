package Banco.controller;

import Banco.dao.ContaCorrenteDao;
import Banco.dao.ContaPoupancaDao;
import Banco.model.ContaCorrente;
import Banco.model.ContaPoupanca;
import Banco.model.Pessoa;
import Banco.service.Conta;
import Banco.util.Utils;

import java.util.ArrayList;
import java.util.Scanner;

public class ContaPoupancaController {
    private static Scanner scanner = new Scanner(System.in);


    public static void abrirConta(String numeroAgencia, String cpf, String senha){
        Pessoa pessoaEncontrada = Utils.isPessoaExiste(cpf);
        if ( pessoaEncontrada != null){
            ContaPoupanca contaPoupanca = new ContaPoupanca(numeroAgencia, pessoaEncontrada, senha);
            ContaPoupancaDao.salvarConta(contaPoupanca);
            System.out.println("Sua conta poupança foi aberta com sucesso!");
        }
        else {
            System.out.println("Não foi possível realizar a abertura de conta!");
        }
    }

    public static void deletarConta(String agencia, String numeroConta){
        ArrayList<ContaPoupanca> listaContaPoupanca = ContaPoupancaDao.lerContas();
        ContaPoupanca  contaPoupancaEncontrada = null;
        for (ContaPoupanca conta : listaContaPoupanca){
            if (conta.getNumeroAgencia() == agencia && conta.getNumeroConta() == numeroConta){
                contaPoupancaEncontrada = conta;
                break;
            }
        }
        if (contaPoupancaEncontrada == null){
            System.out.println("Conta para deletar não existe!");
        }
        else{
            listaContaPoupanca.remove(contaPoupancaEncontrada);
            ContaPoupancaDao.reescreverContas(listaContaPoupanca);
            System.out.println("Conta deletada com sucesso!");
        }

    }

    public static void listarConta(){
        ArrayList<ContaPoupanca> listaContaPoupanca = ContaPoupancaDao.lerContas();
        for (ContaPoupanca conta: listaContaPoupanca){
            System.out.println(conta.toString());
        }
    }

    public static void loginConta(String email, String senha) {
        ContaPoupanca conta = Utils.isContaPoupancaExiste(email);
        if (conta != null) {
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
            } while (opcao != 0);
        } else {
            System.out.println("Não foi possível fazer o login");
        }
    }
    private static void processarOperacao(int opcao, ContaPoupanca conta) {
        ArrayList<ContaPoupanca> lista = ContaPoupancaDao.lerContas();

        switch (opcao){
            case 1:
                double valorDeposito;
                System.out.println("==== DEPÓSITO ====");
                System.out.print("Quantia para depósito: ");
                valorDeposito = scanner.nextDouble();
                scanner.nextLine();
                conta.depositar(valorDeposito);
                atualizarListaContas(lista, conta);
                ContaPoupancaDao.reescreverContas(lista);
                break;

            case 2:
                double valorSaque;
                System.out.println("==== SAQUE ====");
                System.out.print("Quantia para sacar: ");
                valorSaque = scanner.nextDouble();
                scanner.nextLine();
                conta.sacar(valorSaque);
                atualizarListaContas(lista, conta);
                ContaPoupancaDao.reescreverContas(lista);
                break;

            case 3:
                System.out.println("==== TRANSFERÊNCIA ====");
                System.out.print("Informe a conta para transferência: ");
                String numeroConta = scanner.nextLine();
                System.out.print("Digite a quantia: ");
                double valorTransferencia = scanner.nextDouble();
                scanner.nextLine();
                ContaPoupanca contaTransferir = Utils.getContaPoupanca(numeroConta);

                if (contaTransferir != null) {
                    conta.tranferir(contaTransferir, valorTransferencia);
                    atualizarListaContas(lista, conta);
                    atualizarListaContas(lista, contaTransferir);
                    ContaPoupancaDao.reescreverContas(lista);
                } else {
                    System.out.println("Conta destino não encontrada.");
                }
                break;

            case 4:
                conta.exibirExtrato();

            case 0:
                System.out.println("Saindo...");

            default:
                System.out.println("Opção inválida!");
        }
    }

    private static void atualizarListaContas(ArrayList<ContaPoupanca> lista, ContaPoupanca contaAtualizada) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNumeroConta().equals(contaAtualizada.getNumeroConta())) {
                lista.set(i, contaAtualizada);
                return;
            }
        }
    }
}
