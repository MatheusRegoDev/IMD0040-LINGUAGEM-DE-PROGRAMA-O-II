package Banco.view;


import Banco.controller.ContaCorrenteController;
import Banco.controller.ContaPoupancaController;
import Banco.controller.ContaSalarioController;
import Banco.controller.PessoaController;
import Banco.util.Utils;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcaoAgengencia;


        do {
            exibirMenuAgencia();
            opcaoAgengencia = scanner.nextInt();
            scanner.nextLine();
            processarOpcaoAgencia(opcaoAgengencia);
        }while (opcaoAgengencia != 0);
    }

    private static void exibirMenuAgencia(){
        System.out.println("==== SISTEMA MULTI AGÊNCIA BANCÁRIA IMD DIGIRAL ====");
        System.out.println("1. Acessar Agencia Banco do Brasil");
        System.out.println("2. Acessar Agencia Banco do Bradesco");
        System.out.println("3. Acessar Agencia Banco do Itau");
        System.out.println("4. Acessar Controle de pessoas");
        System.out.println("0. sair");
        System.out.print("Digite uma opção: ");
    }

    private static void processarOpcaoAgencia(int opcaoAgencia){
        switch (opcaoAgencia){
            case 1:
                System.out.println("==== Bem-Vindo a agência do Banco do Brasil! ====");
                int opcaoContaBB;
                String agenciaBB = "001";
                do {
                    exibirMenuContas();
                    opcaoContaBB = scanner.nextInt();
                    processarOpcaoMenuContas(opcaoContaBB, agenciaBB);

                }while (opcaoContaBB != 0);
                break;

            case 2:
                System.out.println("==== Bem-Vindo a agência do Banco Bradesco! ====");
                int opcaoContaBradesco;
                String agenciaBradesco = "002";
                do {
                    exibirMenuContas();
                    opcaoContaBradesco = scanner.nextInt();
                    processarOpcaoMenuContas(opcaoContaBradesco, agenciaBradesco);

                }while (opcaoContaBradesco != 0);
                break;

            case 3:
                System.out.println("==== Bem-Vindo a agência do Banco Itaú! ====");
                int opcaoContaItau;
                String agenciaItau = "003";
                do {
                    exibirMenuContas();
                    opcaoContaItau = scanner.nextInt();

                    processarOpcaoMenuContas(opcaoContaItau, agenciaItau);

                }while (opcaoContaItau != 0);
                break;

            case 4:
                System.out.println("==== Bem-Vindo ao Controle de Pessoas! ====");
                int opcao;
                do {
                    exibirMenuPessoas();
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                    processarOpcaoMenuPessoas(opcao);
                }while (opcao != 0);

            case 0:
                System.out.println("Encerrando...");

            default:
                System.out.println("Opção inválida!");
        }
    }

    private static void processarOpcaoMenuPessoas(int opcao) {
        switch (opcao){
            case 1:
                System.out.println("==== CADASTRO DE PESSOAS ====");
                System.out.print("Digite o nome: ");
                String nome = scanner.nextLine();
                System.out.print("Digite o CPF: ");
                String cpf = scanner.nextLine();
                String cpfFormatado = Utils.mascaraCpf(cpf);
                System.out.print("Digite o E-mail: ");
                String email = scanner.nextLine();
                PessoaController.cadastrarPessoa(nome, cpfFormatado, email);
                break;

            case 2:
                System.out.println("==== ALTERAÇÃO DE DADOS ====");
                System.out.print("Digite o CPF que deseja alterar os dados: ");
                String cpfAlterar = scanner.nextLine();
                String cpfAlterarFormatado = Utils.mascaraCpf(cpfAlterar);
                System.out.print("Digite um novo nome: ");
                String novoNome = scanner.nextLine();
                System.out.print("Digite um novo CPF: ");
                String novoCpf = scanner.nextLine();
                String novoCpfFormatado = Utils.mascaraCpf(novoCpf);
                System.out.print("Digite um novo E-mail: ");
                String novoEmail = scanner.nextLine();
                PessoaController.alterPessoa(cpfAlterarFormatado, novoNome, novoCpfFormatado, novoEmail);
                break;

            case 3:
                System.out.println("==== DELETAR PESSOAS ====");
                System.out.print("Digite o cpf para deletar a pessoa: ");
                String cpfDeletar = scanner.nextLine();
                String cpfDeletarFormatado = Utils.mascaraCpf(cpfDeletar);
                PessoaController.deletarPessoa(cpfDeletarFormatado);
                break;

            case 4:
                System.out.println("==== LISTAS DE PESSOAS ====");
                PessoaController.listarPessoas();

            case 0:
                System.out.println("Voltando...");
                break;

            default:
                System.out.println("Opção inválida");
        }
    }

    private static void exibirMenuPessoas(){
        System.out.println("1. Cadastrar Pessoa");
        System.out.println("2. Alterar dados de uma pessoa");
        System.out.println("3. Deletar dados de uma pessoa");
        System.out.println("4. Listar pessoas");
        System.out.println("0. Sair");
    }
    private static void exibirMenuContas(){
        System.out.println("1. Acessar Controle Conta Corrente");
        System.out.println("2. Acessar Controle Conta Poupanca");
        System.out.println("3. Acessar Controle Sálario");
        System.out.println("0. Voltar");
    }

    private static void processarOpcaoMenuContas(int opcaoConta, String agencia){

        switch (opcaoConta){
            case 1:
                int opcaoContaCorrente;
                do {
                    exibirMenuContaCorrente();
                    opcaoContaCorrente = scanner.nextInt();
                    scanner.nextLine();
                    processarOpcaoMenuContaCorrente(opcaoContaCorrente, agencia);
                }while (opcaoContaCorrente != 0);
                break;

            case 2:
                int opcaoContaPoupanca;
                do {
                    exibirMenuContaPoupanca();
                    opcaoContaPoupanca = scanner.nextInt();
                    scanner.nextLine();
                    processarOpcaoMenuContaPoupanca(opcaoContaPoupanca, agencia);
                }while (opcaoContaPoupanca != 0);

            case 3:
                int opcaoContaSalario;
                do {
                    exibirMenuContaSalario();
                    opcaoContaSalario = scanner.nextInt();
                    scanner.nextLine();
                    processarOpcaoMenuContaSalario(opcaoContaSalario, agencia);
                }while (opcaoContaSalario != 0);

            case 0:
                System.out.println("Voltando...");

            default:
                System.out.println("Opção inváçida!");
        }
    }

    private static void exibirMenuContaCorrente(){
        System.out.println("1. Abrir uma Conta Corrente");
        System.out.println("2. Deletar uma Conta Corrente");
        System.out.println("3. Listar Conta Corrente");
        System.out.println("4. Fazer login");
        System.out.println("0. voltar");
    }

    private static void processarOpcaoMenuContaCorrente(int opcaoContaCorrente, String agencia){
        switch (opcaoContaCorrente){
            case 1:
                System.out.println("==== ABERTURA DE CONTA CORRENTE ====");
                System.out.print("Digite seu CPF: ");
                String cpf = scanner.nextLine();
                String cpfFormatado = Utils.mascaraCpf(cpf);
                System.out.print("Crie uma senha: ");
                String senha = scanner.nextLine();
                ContaCorrenteController.abrirConta(agencia, cpfFormatado, senha);
                break;

            case 2:
                System.out.println("==== CANCELAR CONTA CORRENTE ====");
                System.out.print("Digite o número da conta: ");
                String numeroConta = scanner.nextLine();
                ContaCorrenteController.deletarConta(agencia, numeroConta);
                break;

            case 3:
                System.out.println("==== LISTAS DE CONTAS ====");
                ContaCorrenteController.listarConta();
                break;

            case 4:
                System.out.println("==== LOGIN CONTA CORRENTE ====");
                System.out.print("Digite seu e-mail: ");
                String email = scanner.nextLine();
                System.out.print("Digite sua senha: ");
                String senhaLogin = scanner.nextLine();
                ContaCorrenteController.loginConta(email, senhaLogin);

            case 0:
                System.out.println("voltando...");
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }

    private static void exibirMenuContaPoupanca(){
        System.out.println("1. Abrir uma Conta Poupança");
        System.out.println("2. Deletar uma Conta Poupança");
        System.out.println("3. Listar Conta Poupança");
        System.out.println("4. Fazer login");
        System.out.println("0. voltar");
    }

    private static void processarOpcaoMenuContaPoupanca(int opcaoContaPoupanca, String agencia) {
        switch (opcaoContaPoupanca) {
            case 1:
                System.out.println("==== ABERTURA DE CONTA POUPANÇA ====");
                System.out.print("Digite seu CPF: ");
                String cpf = scanner.nextLine();
                String cpfFormatado = Utils.mascaraCpf(cpf);
                System.out.print("Crie uma senha: ");
                String senha = scanner.nextLine();
                ContaPoupancaController.abrirConta(agencia, cpfFormatado, senha);
                break;

            case 2:
                System.out.println("==== CANCELAR CONTA POUPANÇA ====");
                System.out.print("Digite o número da conta: ");
                String numeroConta = scanner.nextLine();
                ContaPoupancaController.deletarConta(agencia, numeroConta);
                break;

            case 3:
                System.out.println("==== LISTAS DE CONTAS ====");
                ContaPoupancaController.listarConta();
                break;

            case 4:
                System.out.println("==== LOGIN CONTA POUPANÇA ====");
                System.out.print("Digite seu e-mail: ");
                String email = scanner.nextLine();
                System.out.print("Digite sua senha: ");
                String senhaLogin = scanner.nextLine();
                ContaPoupancaController.loginConta(email, senhaLogin);

            case 0:
                System.out.println("voltando...");
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }

    private static void exibirMenuContaSalario(){
        System.out.println("1. Abrir uma Conta Salário");
        System.out.println("2. Deletar uma Conta Salário");
        System.out.println("3. Listar Conta Salário");
        System.out.println("4. Fazer login");
        System.out.println("0. voltar");
    }

    private static void processarOpcaoMenuContaSalario(int opcaoContaSalario, String agencia) {
        switch (opcaoContaSalario) {
            case 1:
                System.out.println("==== ABERTURA DE CONTA SALÁRIO ====");
                System.out.print("Digite seu CPF: ");
                String cpf = scanner.nextLine();
                String cpfFormatado = Utils.mascaraCpf(cpf);
                System.out.print("Crie uma senha: ");
                String senha = scanner.nextLine();
                ContaSalarioController.abrirConta(agencia, cpfFormatado, senha);
                break;

            case 2:
                System.out.println("==== CANCELAR CONTA SALÁRIO  ====");
                System.out.print("Digite o número da conta: ");
                String numeroConta = scanner.nextLine();
                ContaSalarioController.deletarConta(agencia, numeroConta);
                break;

            case 3:
                System.out.println("==== LISTAS DE CONTAS ====");
                ContaSalarioController.listarConta();
                break;

            case 4:
                System.out.println("==== LOGIN CONTA POUPANÇA ====");
                System.out.print("Digite seu e-mail: ");
                String email = scanner.nextLine();
                System.out.print("Digite sua senha: ");
                String senhaLogin = scanner.nextLine();
                ContaSalarioController.loginConta(email, senhaLogin);

            case 0:
                System.out.println("voltando...");
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }
}