package Banco.dao;

import Banco.model.ContaCorrente;
import Banco.model.Movimentacao;
import Banco.model.Pessoa;
import Banco.util.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContaCorrenteDao{
    private static String arquivo = "./dados/ContaCorrente.csv";

    public static void salvarConta(ContaCorrente conta){
        try {
            boolean isArquivoExiste = new File(arquivo).exists();
            FileWriter escritor = new FileWriter(arquivo, StandardCharsets.UTF_8,true);

            if (!isArquivoExiste) {
                escritor.write("Número Agência;Número da Conta;Nome Titular;CPF titular;E-mail Titular;" +
                        "Saldo;Senha da conta;Útima data de manutenção;Movimentações\n");
            }

            String movimentacoes = Utils.separarMovimentacao(conta.getExtrato());
            if (movimentacoes == null || movimentacoes.isEmpty()) {
                movimentacoes = "Nenhuma movimentação";
            }

            escritor.write(conta.getNumeroAgencia() + ";" + conta.getNumeroConta() + ";" +
                    conta.getTitular().getNome() + ";" + conta.getTitular().getCpf() + ";" +
                    conta.getTitular().getEmail() + ";" + conta.getSaldo() + ";" + conta.getSenha() + ";" +
                    conta.getUltimaDataManutecao() + ";" + movimentacoes + "\n");
            escritor.flush();
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ContaCorrente> lerContas() {
        ArrayList<ContaCorrente> lista = new ArrayList<>();
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = leitor.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }


                String[] partes = linha.split(";");
                if (partes.length < 9) {
                    System.out.println("Linha mal formatada: " + linha);
                    continue;
                }

                try {

                    String numeroAgencia = partes[0];
                    String numeroConta = partes[1];
                    String nomeTitular = partes[2];
                    String cpfTitular = partes[3];
                    String emailTitular = partes[4];
                    Double saldo = Double.valueOf(partes[5]);
                    String senha = partes[6];
                    LocalDate ultimaDataManutencao = LocalDate.parse(partes[7]);
                    String movimentacoes = partes[8];

                    ContaCorrente conta = new ContaCorrente(
                            numeroAgencia,
                            new Pessoa(nomeTitular, cpfTitular, emailTitular),
                            senha
                    );
                    conta.setNumeroConta(numeroConta);
                    conta.setSaldo(saldo);
                    conta.setUltimaDataManuntecao(ultimaDataManutencao);

                    // Processar movimentações
                    ArrayList<Movimentacao> movimentacao = Utils.juntarMovimentacao(movimentacoes);
                    conta.setHistorico(movimentacao);

                    // Adicione a conta à lista
                    lista.add(conta);
                } catch (Exception e) {
                    System.out.println("Erro ao processar linha: " + linha);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();}

        return lista;
    }


    public static void reescreverContas(ArrayList<ContaCorrente> listaContaCorrente){
        try (FileWriter escritor = new FileWriter(arquivo, StandardCharsets.UTF_8, false)) {
            escritor.write("Número Agência;Número da Conta;Nome Titular;CPF titular;E-mail Titular;" +
                    "Saldo;Senha da conta;Última data de manuntenção;Movimentações\n");

            for (ContaCorrente contaCorrente : listaContaCorrente){
                escritor.write(contaCorrente.getNumeroAgencia() + ";" + contaCorrente.getNumeroConta() + ";" +
                        contaCorrente.getTitular().getNome() + ";" + contaCorrente.getTitular().getCpf() + ";" +
                        contaCorrente.getTitular().getEmail() + ";" + contaCorrente.getSaldo() + ";" + contaCorrente.getSenha() + ";" +
                        contaCorrente.getUltimaDataManutecao() + ";" + Utils.separarMovimentacao(contaCorrente.getExtrato()) + "\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
