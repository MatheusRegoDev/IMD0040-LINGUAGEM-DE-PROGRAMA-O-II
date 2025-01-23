package Banco.controller;

import Banco.dao.PessoaDao;
import Banco.model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PessoaController {
    public static void cadastrarPessoa(String nome, String cpf, String email){
        ArrayList<Pessoa> lista = PessoaDao.lerPessoas();

        List<Pessoa> pessoaFiltrada = lista.stream().filter(pessoa -> pessoa.getCpf().equals(cpf)).collect(Collectors.toList());

        if (pessoaFiltrada.isEmpty()){
            Pessoa pessoa = new Pessoa(nome, cpf, email);
            PessoaDao.salvarPessoa(pessoa);
            System.out.println("Pessoa Cadastrada com sucesso!");
        }
        else {
            System.out.println("Pessoa já existe!");
        }
    }

    public static void alterPessoa(String cpfAlterar, String novoNome, String novoCpf, String novoEmail){
        ArrayList<Pessoa> lista = PessoaDao.lerPessoas();
        boolean pessoaEncontrada = false;
        for (Pessoa pessoa : lista){
            if (pessoa.getCpf().equals(cpfAlterar)){
                pessoa.setNome(novoNome);
                pessoa.setCpf(novoCpf);
                pessoa.setEmail(novoEmail);
                pessoaEncontrada = true;
                break;
            }
        }
        if (!pessoaEncontrada) {
            System.out.println("Pessoa com não encotrada");
        }
        else {
            PessoaDao.reescreverPessoas(lista);
            System.out.println("Pessoa alterada com sucesso!");
        }
    }

    public static void deletarPessoa(String cpfDeletar){
        ArrayList<Pessoa> lista = PessoaDao.lerPessoas();
        Pessoa pessoaEncontrada = null;
        for (Pessoa pessoa : lista){
            if (pessoa.getCpf().equals(cpfDeletar)){
                pessoaEncontrada = pessoa;
                break;
            }
        }
        if (pessoaEncontrada == null) {
            System.out.println("Pessoa com não encotrada");
        }
        else {
            lista.remove(pessoaEncontrada);
            PessoaDao.reescreverPessoas(lista);
            System.out.println("Pessoa deletada com sucesso!");
        }
    }

    public static void listarPessoas(){
        ArrayList<Pessoa> lista = PessoaDao.lerPessoas();
        for (Pessoa pessoa : lista){
            System.out.println(pessoa.toString());
        }
    }

}
