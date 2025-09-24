package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoInicializador {

    public static void criarTabela() {
        String sql = """
                CREATE TABLE IF NOT EXISTS pessoas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome VARCHAR(100) not NULL,
                idade INTEGER not NULL,
                email VARCHAR(100) not NULL
                );
                """;

        try (
                Connection conn = Conexao.conectar();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar Tabela: " + e.getMessage());
        }
    }
}
