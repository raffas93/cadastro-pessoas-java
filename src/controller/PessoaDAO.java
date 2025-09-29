package controller;

import model.Pessoa;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    public boolean salvar(Pessoa p) {
        String sql = "INSERT INTO pessoas (nome, idade, email, cpf) VALUES (?,?,?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getCpf());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
            return false;
        }
    }

    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<Pessoa>();
        String sql = "SELECT id, nome, idade, email, cpf FROM pessoas";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf");

                Pessoa p = new Pessoa(id, nome, idade, email, cpf);
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listarTodos: " + e.getMessage());
        }
        return lista;
    }

    public Pessoa buscarPorCpf(String cpfBuscado) {
        String sql = "SELECT id, nome, idade, email, cpf FROM pessoas WHERE cpf = ? ";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfBuscado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf");
                return new Pessoa(id, nome, idade, email, cpf);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar por CPF: " + e.getMessage());
        }

        return null;

    }

    public boolean editar(String cpfBuscado, Pessoa novosDados) {
        String sql = "UPDATE pessoas SET nome = ?, idade = ?, email = ? WHERE cpf = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novosDados.getNome());
            stmt.setInt(2, novosDados.getIdade());
            stmt.setString(3, novosDados.getEmail());
            stmt.setString(4, cpfBuscado);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao editar pessoa: " + e.getMessage());
            return false;
        }
    }


    public boolean removerPorCpf(String cpfRemover) {
        String sql = "DELETE FROM pessoas WHERE cpf = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfRemover);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao remover pessoa: " + e.getMessage());
            return false;
        }
    }

    public boolean exportarPessoas(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Pessoa p : listarTodos()) {
                writer.write(p.getId() + ";" + p.getNome() + ";" + p.getIdade() + ";" + p.getEmail() + ";" + p.getCpf());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao exportar arquivo: " + e.getMessage());
            return false;
        }
    }

    public boolean importarPessoas(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                //tratar cada linha
                String [] partes = linha.split(";");
                if (partes.length == 5) {
                    String nome = partes[1].trim();
                    int idade = Integer.parseInt(partes[2].trim());
                    String email = partes[3].trim();
                    String cpf = partes[4].trim();
                    Pessoa p = new Pessoa(nome, idade, email, cpf);
                    if (buscarPorCpf(cpf) == null) {
                        salvar(p);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao importar arquivo: " + e.getMessage());
            return false;
        }
    }
}
