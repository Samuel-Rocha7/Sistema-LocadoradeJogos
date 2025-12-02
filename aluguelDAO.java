package dao;

import Locadora.ConexaoPostgreSQL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class aluguelDAO {
    
    public boolean realizarAluguel(int idCliente, int idJogo, int dias) {
        String sqlAluguel = "INSERT INTO aluguel (id_cliente, id_jogo, data_devolucao_prevista, valor_total, status) VALUES (?, ?, ?, ?, 'ATIVO')";
        String sqlAtualizaJogo = "UPDATE jogo SET quantidade = quantidade - 1 WHERE id_jogo = ?";
        
        Connection conn = null;
        try {
            conn = ConexaoPostgreSQL.getConexao();
            conn.setAutoCommit(false);
            
            double valorDiario = buscarValorJogo(idJogo, conn);
            double valorTotal = valorDiario * dias;
            
            try (PreparedStatement pstmtAluguel = conn.prepareStatement(sqlAluguel)) {
                pstmtAluguel.setInt(1, idCliente);
                pstmtAluguel.setInt(2, idJogo);
                pstmtAluguel.setDate(3, Date.valueOf(LocalDate.now().plusDays(dias)));
                pstmtAluguel.setDouble(4, valorTotal);
                pstmtAluguel.executeUpdate();
            }
            
            try (PreparedStatement pstmtJogo = conn.prepareStatement(sqlAtualizaJogo)) {
                pstmtJogo.setInt(1, idJogo);
                pstmtJogo.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Erro ao realizar aluguel: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private double buscarValorJogo(int idJogo, Connection conn) throws SQLException {
        String sql = "SELECT valor_aluguel_diario FROM jogo WHERE id_jogo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idJogo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("valor_aluguel_diario");
            }
        }
        return 0.0;
    }
    
    public List<String> listarAtivos() {
        List<String> alugueis = new ArrayList<>();
        String sql = "SELECT a.id_aluguel, c.nome as cliente, j.titulo as jogo, a.data_aluguel, a.data_devolucao_prevista, a.valor_total FROM aluguel a JOIN cliente c ON a.id_cliente = c.id_cliente JOIN jogo j ON a.id_jogo = j.id_jogo WHERE a.status = 'ATIVO' ORDER BY a.data_aluguel DESC";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String linha = String.format("Aluguel ID: %d | Cliente: %s | Jogo: %s | Alugado em: %s | Devolução: %s | Valor: R$ %.2f",
                    rs.getInt("id_aluguel"),
                    rs.getString("cliente"),
                    rs.getString("jogo"),
                    rs.getDate("data_aluguel"),
                    rs.getDate("data_devolucao_prevista"),
                    rs.getDouble("valor_total")
                );
                alugueis.add(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar aluguéis ativos: " + e.getMessage());
        }
        return alugueis;
    }
    
    public boolean devolverJogo(int idAluguel) {
        String sqlDevolver = "UPDATE aluguel SET status = 'FINALIZADO', data_devolucao_real = CURRENT_DATE WHERE id_aluguel = ?";
        String sqlAtualizaJogo = "UPDATE jogo j SET quantidade = quantidade + 1 FROM aluguel a WHERE j.id_jogo = a.id_jogo AND a.id_aluguel = ?";
        
        Connection conn = null;
        try {
            conn = ConexaoPostgreSQL.getConexao();
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmtJogo = conn.prepareStatement(sqlAtualizaJogo)) {
                pstmtJogo.setInt(1, idAluguel);
                pstmtJogo.executeUpdate();
            }
            
            try (PreparedStatement pstmtAluguel = conn.prepareStatement(sqlDevolver)) {
                pstmtAluguel.setInt(1, idAluguel);
                pstmtAluguel.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Erro ao devolver jogo: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean excluir(int id) {
        String sql = "DELETE FROM aluguel WHERE id_aluguel = ?";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluguel: " + e.getMessage());
        }
        return false;
    }
}