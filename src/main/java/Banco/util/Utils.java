package Banco.util;

import Banco.dao.ContaCorrenteDao;
import Banco.dao.ContaPoupancaDao;
import Banco.dao.ContaSalarioDao;
import Banco.dao.PessoaDao;
import Banco.model.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {
    public static String mascaraCpf(String cpf){
        if (cpf.length() != 11){
            System.out.println("O CPF deve conter 11 dígitos!");
        }
        return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
    static NumberFormat formatarValores  = new DecimalFormat("R$ #,##0.00");

    public static String valorToString (Double valor){

        return formatarValores.format(valor);
    }

    public static String gerarNumeroConta(){
        Random random = new Random();
        int numero = 10000000 + random.nextInt(90000000);
        int digitoVerificador = random.nextInt(10);
        return  numero + "-" + digitoVerificador;
    }

    public static String separarMovimentacao(ArrayList<Movimentacao> movimentacao){
        if (movimentacao == null || movimentacao.equals("Nenhuma movimentação")){
            return "Nenhuma movimentação";
        }
        StringBuilder builder = new StringBuilder();
        for (Movimentacao movimento : movimentacao){
            builder.append(movimento.getTipo()).append("|")
                    .append(movimento.getValor()).append("|")
                    .append(movimento.getDataHora()).append("|")
                    .append(movimento.getDescricao() == null ? "" : movimento.getDescricao()).append(",");
        }
        if (builder.length() > 0){
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }

    public static ArrayList<Movimentacao> juntarMovimentacao(String movimentacao){
        ArrayList<Movimentacao> listaMovimentacao = new ArrayList<>();
        if (movimentacao.isEmpty() || movimentacao.equals("Nenhuma movimentação")){
            return new ArrayList<Movimentacao>();
        }

        String[] arraymovimentacoes = movimentacao.split(",");
        for (String movimento : arraymovimentacoes){
            String[] partes = movimento.split("\\|");
            String tipo = partes[0];
            double valor = Double.parseDouble(partes[1]);
            LocalDateTime dateHora = LocalDateTime.parse(partes[2]);
            String descricao = partes.length > 3 ? partes[3] : null;

            Movimentacao auxMovimentacao = new Movimentacao(tipo, valor, descricao);
            auxMovimentacao.setDataHora(dateHora);
            listaMovimentacao.add(auxMovimentacao);
        }

        return listaMovimentacao;
    }

    public static Pessoa isPessoaExiste(String cpf) {
        ArrayList<Pessoa> listaPessoa = PessoaDao.lerPessoas();
        return listaPessoa.stream()
                .filter(pessoa -> pessoa.getCpf().equals(cpf))
                .findFirst()
                .orElse(null); //
    }


    public static ContaCorrente isContaCorrenteExiste(String email){
        ArrayList<ContaCorrente> listaContaCorrente = ContaCorrenteDao.lerContas();
        List<ContaCorrente> contaFiltrada = listaContaCorrente.stream()
                .filter(contaCorrente -> contaCorrente.getTitular().getEmail().equals(email))
                .collect(Collectors.toList());
        if (contaFiltrada.isEmpty()){
            return null;
        }
        return contaFiltrada.get(0);
    }

    public static ContaPoupanca isContaPoupancaExiste(String email) {
        ArrayList<ContaPoupanca> listaContaPoupanca = ContaPoupancaDao.lerContas();

        List<ContaPoupanca> contaFiltrada = listaContaPoupanca.stream()
                .filter(contaPoupanca -> contaPoupanca.getTitular() != null &&
                        contaPoupanca.getTitular().getEmail() != null &&
                        contaPoupanca.getTitular().getEmail().trim().equalsIgnoreCase(email.trim()))
                .collect(Collectors.toList());
        if (contaFiltrada.isEmpty()) {
            return null;
        }
        return contaFiltrada.get(0);
    }


    public static ContaSalario isContaSalarioExiste(String email){
        ArrayList<ContaSalario> listaContaSalario = ContaSalarioDao.lerContas();
        List<ContaSalario> contaFiltrada = listaContaSalario.stream()
                .filter(contaSalario -> contaSalario.getTitular().getEmail().equals(email))
                .collect(Collectors.toList());
        if (contaFiltrada.isEmpty()){
            return null;
        }
        return contaFiltrada.get(0);
    }

    public static ContaCorrente getContaCorrente(String numeroConta){
        ArrayList<ContaCorrente> listaContaCorrente = ContaCorrenteDao.lerContas();
        List<ContaCorrente> contaFiltrada = listaContaCorrente.stream()
                .filter(contaCorrente -> contaCorrente.getNumeroConta().equals(numeroConta))
                .collect(Collectors.toList());
        if (contaFiltrada.isEmpty()){
            return null;
        }
        return contaFiltrada.get(0);
    }

    public static ContaPoupanca getContaPoupanca(String numeroConta) {
        ArrayList<ContaPoupanca> listaContaPoupanca = ContaPoupancaDao.lerContas();
        List<ContaPoupanca> contaFiltrada = listaContaPoupanca.stream()
                .filter(contaPoupanca -> contaPoupanca.getNumeroConta().equals(numeroConta))
                .collect(Collectors.toList());
        if (contaFiltrada.isEmpty()) {
            return null;
        }
        return contaFiltrada.get(0);
    }

    public static ContaSalario getContaSalario(String numeroConta){
        ArrayList<ContaSalario> listaContaSalario = ContaSalarioDao.lerContas();
        List<ContaSalario> contaFiltrada = listaContaSalario.stream()
                .filter(contaSalario -> contaSalario.getNumeroConta().equals(numeroConta))
                .collect(Collectors.toList());
        if (contaFiltrada.isEmpty()){
            return null;
        }
        return contaFiltrada.get(0);
    }

    public static void criarArquivosCsv() {
        // Caminho para o diretório
        String diretorioDados = "./dados";

        // Lista de nomes de arquivos
        String[] arquivos = {
                "pessoa.csv",
                "ContaPoupanca.csv",
                "ContaCorrente.csv",
                "ContaSalario.csv"
        };

        try {
            File diretorio = new File(diretorioDados);
            if (!diretorio.exists()) {
                if (diretorio.mkdirs()) {
                    System.out.println("Diretório criado: " + diretorioDados);
                } else {
                    System.err.println("Falha ao criar o diretório: " + diretorioDados);
                    return;
                }
            }

            for (String nomeArquivo : arquivos) {
                File arquivo = new File(diretorio, nomeArquivo);
                if (!arquivo.exists()) {
                    if (arquivo.createNewFile()) {
                        System.out.println("Arquivo criado: " + arquivo.getAbsolutePath());
                    } else {
                        System.err.println("Falha ao criar o arquivo: " + arquivo.getName());
                    }
                } else {
                    System.out.println("Arquivo já existe: " + arquivo.getAbsolutePath());
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao criar os arquivos ou diretório: " + e.getMessage());
        }
    }

}
