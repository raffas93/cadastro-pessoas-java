package controller;

import model.Pessoa;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Cadastro {

    private List<Pessoa> pessoas = new ArrayList<>();

    /**
     * Adiciona uma nova pessoa ao cadastro.
     * Retorna false se já existir alguém com o mesmo nome.
     */
    public boolean adicionarPessoa(Pessoa p) {
        if (buscarPessoa(p.getNome()) != null) {
            return false; //já existe na base
        }
        return pessoas.add(p);
    }

    public List<Pessoa> listarPessoas() {
        return new ArrayList<>(pessoas);
    }

    public Pessoa buscarPessoa(String nomeBuscado) {
        for (Pessoa p : pessoas) {
            if (p.getNome().equalsIgnoreCase(nomeBuscado)) {
                return p;
            }
        }
        return null;
    }

    public boolean removerPessoa(String nomeRemovido) {
        Pessoa p = buscarPessoa(nomeRemovido);
        if (p != null) {
            return pessoas.remove(p);
        }
        return false;
    }

    public boolean editarPessoa(String nome, Pessoa novosDados) {
        Pessoa p = buscarPessoa(nome);
        if (p != null) {
            p.setNome(novosDados.getNome());
            p.setIdade(novosDados.getIdade());
            p.setEmail(novosDados.getEmail());
            return true;
        }
        return false;
    }

    public boolean exportarPessoas(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Pessoa p : pessoas) {
                writer.write(p.getNome() + "; " + p.getIdade() + "; " + p.getEmail());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean importarPessoas(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                //tratar cada linha
                String [] partes = linha.split(";");
                if (partes.length == 3) {
                    String nome = partes[0].trim();
                    int idade = Integer.parseInt(partes[1].trim());
                    String email = partes[2].trim();
                    String cpf = partes[3].trim();
                    Pessoa p = new Pessoa(nome, idade, email, cpf);
                    pessoas.add(p);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
