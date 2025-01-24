package Banco.dao;

import Banco.model.ContaPoupanca;
import Banco.model.Movimentacao;
import Banco.model.Pessoa;

import Banco.util.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContaPoupancaDao {
    private static String arquivo = "./dados/ContaPoupanca.csv";

    public static void salvarConta(ContaPoupanca conta){
        try {
            boolean isArquivoExiste = new File(arquivo).exists();
            FileWriter escritor = new FileWriter(arquivo, StandardCharsets.UTF_8,true);

            if (!isArquivoExiste) {
                escritor.write("Número Agência;Número da Conta;Nome Titular;CPF titular;E-mail Titular;" +
                        "Saldo;Senha da conta;Última data de rendimento;Movimentações\n");
            }

            String movimentacoes = Utils.separarMovimentacao(conta.getExtrato());
            if (movimentacoes == null || movimentacoes.isEmpty()) {
                movimentacoes = "Nenhuma movimentação";
            }

            escritor.write(conta.getNumeroAgencia() + ";" + conta.getNumeroConta() + ";" +
                    conta.getTitular().getNome() + ";" + conta.getTitular().getCpf() + ";" +
                    conta.getTitular().getEmail() + ";" + conta.getSaldo() + ";" + conta.getSenha() + ";" +
                    conta.getUltimaDataRendimento() + ";" + movimentacoes + "\n");
            escritor.flush();
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ContaPoupanca> lerContas(){
        ArrayList<ContaPoupanca> lista = new ArrayList<>();
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));

            String linha;
            boolean primeiraLinha = true;

            while ((linha = leitor.readLine()) != null){
                if (primeiraLinha){
                    primeiraLinha = false;
                    continue;
                }

                try{
                String[] partes = linha.split(";");
                String numeroAgencia = partes[0];
                String numeroConta = partes[1];
                String nomeTitular = partes[2];
                String cpfTitular = partes[3];
                String emailTtitular = partes[4];
                Double saldo = Double.valueOf(partes[5]);
                String senha = partes[6];
                LocalDate  ultimaDataRendimento = LocalDate.parse(partes[7]);
                String movimentacoes = partes[8];

                ContaPoupanca conta = new ContaPoupanca(
                        numeroAgencia,
                        new Pessoa(nomeTitular,cpfTitular, emailTtitular),
                        senha);

                conta.setNumeroConta(numeroConta);
                conta.setSaldo(saldo);
                conta.setUltimaDataRendimento(ultimaDataRendimento);

                ArrayList<Movimentacao> movimentacao = Utils.juntarMovimentacao(movimentacoes);
                conta.setHistorico(movimentacao);

                lista.add(conta);
                }catch (Exception e) {
                    System.out.println("Erro ao processar linha: " + linha);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void reescreverContas(ArrayList<ContaPoupanca> listaContaPoupanca){
        try (FileWriter escritor = new FileWriter(arquivo, StandardCharsets.UTF_8, false)) {
            escritor.write("Número Agência;Número da Conta;Nome Titular;CPF titular;E-mail Titular;" +
                    "Saldo;Senha da conta;Última data de rendimento;Movimentações\n");

            for (ContaPoupanca contaPoupanca : listaContaPoupanca){
                escritor.write(contaPoupanca.getNumeroAgencia() + ";" + contaPoupanca.getNumeroConta() + ";" +
                        contaPoupanca.getTitular().getNome() + ";" + contaPoupanca.getTitular().getCpf() + ";" +
                        contaPoupanca.getTitular().getEmail() + ";" + contaPoupanca.getSaldo() + ";" + contaPoupanca.getSenha() + ";" +
                        contaPoupanca.getUltimaDataRendimento() + ";" + Utils.separarMovimentacao(contaPoupanca.getExtrato()) + "\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
