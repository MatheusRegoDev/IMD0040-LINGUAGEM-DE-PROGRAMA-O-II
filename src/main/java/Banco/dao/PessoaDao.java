package Banco.dao;

import Banco.model.Pessoa;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PessoaDao {
    private static final String CAMINHO_ARQUIVO = "./dados/pessoa.csv";

    public static void salvarPessoa(Pessoa pessoa){
        try {
            boolean isArquivoExiste = new File(CAMINHO_ARQUIVO).exists();

            FileWriter escritor = new FileWriter(CAMINHO_ARQUIVO, StandardCharsets.UTF_8,true);

            if (!isArquivoExiste) {
                escritor.write("Nome;CPF;E-mail\n");
            }
            escritor.write(pessoa.getNome() + ";" + pessoa.getCpf() + ";" + pessoa.getEmail() + "\n");
            escritor.flush();
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Pessoa> lerPessoas(){
        ArrayList<Pessoa> lista = new ArrayList<>();

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO));

            String linha;
            boolean primeiraLinha = true;

            while ((linha = leitor.readLine()) != null){
                if (primeiraLinha){
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(";");
                String nome = partes[0];
                String cpf = partes[1];
                String email = partes[2];

                Pessoa pessoa = new Pessoa(nome, cpf, email);
                lista.add(pessoa);
            }
            leitor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static void reescreverPessoas(ArrayList<Pessoa> listaPessoas) {
        try (FileWriter escritor = new FileWriter(CAMINHO_ARQUIVO, StandardCharsets.UTF_8, false)) {
            escritor.write("Nome;CPF;E-mail\n");
            for (Pessoa pessoa : listaPessoas) {
                escritor.write(pessoa.getNome() + ";" + pessoa.getCpf() + ";" + pessoa.getEmail() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
