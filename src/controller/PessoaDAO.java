package controller;

import model.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    public boolean salvar(Pessoa p) {
        String sql = "INSERT INTO pessoas (nome, idade, email) VALUES (?,?,?)";

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getEmail());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
            return false;
        }
    }

    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<Pessoa>();
        String sql = "SELECT nome, idade, email FROM pessoas";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String email = rs.getString("email");

                Pessoa p = new Pessoa(nome, idade, email);
                lista.add(p);
            }
        }catch (SQLException e) {
            System.out.println("Erro ao listarTodos: " + e.getMessage());
        }
        return lista;
    }

    public Pessoa buscarPorNome(String nomeBuscado) {
        String sql = "SELECT nome, idade, email FROM pessoas WHERE nome LIKE ? ";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,nomeBuscado + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String email = rs.getString("email");
                return new Pessoa(nome, idade, email);
            }
    } catch (Exception e) {
            System.out.println("Erro ao buscarPorNome: " + e.getMessage());
        }

    return null;

    }

    public boolean editar(String nomeOriginal, Pessoa novosDados) {
        String sql = "UPDATE pessoas SET nome = ?, idade = ?, email = ? WHERE nome LIKE ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novosDados.getNome());
            stmt.setInt(2, novosDados.getIdade());
            stmt.setString(3, novosDados.getEmail());
            stmt.setString(4, nomeOriginal + "%");

        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao editar pessoa: " + e.getMessage());
            return false;
        }


    }



}
