package Locadora;

import java.sql.*;

public class ConexaoPostgreSQL {
    private static final String URL = "jdbc:postgresql://localhost:5432/locadora_jogos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "pgadmin";
    
    private static Connection conexao;
    
    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão estabelecida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
        return conexao;
    }
    
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
    
    public static void testarConexao() {
        try (Connection conn = getConexao()) {
            System.out.println("Conexão com PostgreSQL funcionando!");
        } catch (SQLException e) {
            System.err.println("Falha na conexão: " + e.getMessage());
        }
    }
}
