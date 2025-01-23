package Banco.dao;

import Banco.model.ContaSalario;
import Banco.model.Movimentacao;
import Banco.model.Pessoa;
import Banco.util.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContaSalarioDao {
    private static String arquivo = "./dados/ContaSalario.csv";

    public static void salvarConta(ContaSalario conta){
        try {
            boolean isArquivoExiste = new File(arquivo).exists();
            FileWriter escritor = new FileWriter(arquivo, StandardCharsets.UTF_8,true);

            if (!isArquivoExiste) {
                escritor.write("Número Agência;Número da Conta;Nome Titular;CPF titular;E-mail Titular;" +
                        "Saldo;Senha da conta;Qtd de saques realizados hoje;Última data de Saque;Movimentações\n");
            }

            String movimentacoes = Utils.separarMovimentacao(conta.getExtrato());
            if (movimentacoes == null || movimentacoes.isEmpty()) {
                movimentacoes = "Nenhuma movimentação";
            }

            escritor.write(conta.getNumeroAgencia() + ";" + conta.getNumeroConta() + ";" +
                    conta.getTitular().getNome() + ";" + conta.getTitular().getCpf() + ";" +
                    conta.getTitular().getEmail() + ";" + conta.getSaldo() + ";" + conta.getSenha() + ";" +
                    conta.getSaquesRealizadosHoje() + ";" + conta.getUltimaDataSaque() + ";" +
                    movimentacoes + "\n");
            escritor.flush();
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ContaSalario> lerContas(){
        ArrayList<ContaSalario> lista = new ArrayList<>();
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));

            String linha;
            boolean primeiraLinha = true;

            while ((linha = leitor.readLine()) != null){
                if (primeiraLinha){
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(";");
                String numeroAgencia = partes[0];
                String numeroConta = partes[1];
                String nomeTitular = partes[2];
                String cpfTitular = partes[3];
                String emailTtitular = partes[4];
                Double saldo = Double.valueOf(partes[5]);
                String senha = partes[6];
                int qtdSaqueHoje = Integer.parseInt(partes[7]);
                LocalDate ultimaDataSaque = partes[8].isEmpty() ? null : LocalDate.parse(partes[8]);
                String movimentacoes = partes[9];

                ContaSalario conta = new ContaSalario(numeroAgencia, new Pessoa(nomeTitular,cpfTitular,
                        emailTtitular), senha);
                conta.setNumeroConta(numeroConta);
                conta.setSaldo(saldo);
                conta.setSaquesRealizadosHoje(qtdSaqueHoje);
                conta.setUltimaDataSaque(ultimaDataSaque);
                ArrayList<Movimentacao> movimentacao = Utils.juntarMovimentacao(movimentacoes);
                conta.setHistorico(movimentacao);
                lista.add(conta);
            }
            leitor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static void reescreverContas(ArrayList<ContaSalario> listaContaSalario){
        try (FileWriter escritor = new FileWriter(arquivo, StandardCharsets.UTF_8, false)) {
            escritor.write("Número Agência;Número da Conta;Nome Titular;CPF titular;E-mail Titular;" +
                    "Saldo;Senha da conta;Qtd de saques realizados hoje;Última data de Saque;Movimentações\n");

            for (ContaSalario contaSalario : listaContaSalario){
                escritor.write(contaSalario.getNumeroAgencia() + ";" + contaSalario.getNumeroConta() + ";" +
                        contaSalario.getTitular().getNome() + ";" + contaSalario.getTitular().getCpf() + ";" +
                        contaSalario.getTitular().getEmail() + ";" + contaSalario.getSaldo() + ";" + contaSalario.getSenha() +
                        contaSalario.getSaquesRealizadosHoje() + ";" + contaSalario.getUltimaDataSaque() + ";" +
                        Utils.separarMovimentacao(contaSalario.getExtrato()) + "\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
