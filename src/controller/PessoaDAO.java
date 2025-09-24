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
}
