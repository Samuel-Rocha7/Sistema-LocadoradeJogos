package dao;

import Locadora.ConexaoPostgreSQL;
import model.jogo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jogoDAO {
    

    public boolean inserir(jogo jogo) {
        String sql = "INSERT INTO jogo (titulo, id_plataforma, id_genero, ano_lancamento, " +
                     "desenvolvedora, quantidade, valor_aluguel_diario) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, jogo.getTitulo());
            pstmt.setInt(2, jogo.getIdPlataforma());
            pstmt.setInt(3, jogo.getIdGenero());
            pstmt.setInt(4, jogo.getAnoLancamento());
            pstmt.setString(5, jogo.getDesenvolvedora());
            pstmt.setInt(6, jogo.getQuantidade());
            pstmt.setDouble(7, jogo.getValorAluguelDiario());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    jogo.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir jogo: " + e.getMessage());
        }
        return false;
    }
    
    public List<String> listarComDetalhes() {
        List<String> jogos = new ArrayList<>();
        String sql = "SELECT j.id_jogo, j.titulo, p.nome as plataforma, " +
                     "g.nome as genero, j.quantidade, j.valor_aluguel_diario " +
                     "FROM jogo j " +
                     "JOIN plataforma p ON j.id_plataforma = p.id_plataforma " +
                     "JOIN genero g ON j.id_genero = g.id_genero " +
                     "ORDER BY j.titulo";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String linha = String.format(
                    "ID: %d | Jogo: %s | Plataforma: %s | Gênero: %s | " +
                    "Disponíveis: %d | R$ %.2f/dia",
                    rs.getInt("id_jogo"),
                    rs.getString("titulo"),
                    rs.getString("plataforma"),
                    rs.getString("genero"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor_aluguel_diario")
                );
                jogos.add(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar jogos: " + e.getMessage());
        }
        return jogos;
    }
    
    public List<String> listarDisponiveis() {
        List<String> jogos = new ArrayList<>();
        String sql = "SELECT j.id_jogo, j.titulo, p.nome as plataforma, " +
                     "g.nome as genero, j.quantidade, j.valor_aluguel_diario " +
                     "FROM jogo j " +
                     "JOIN plataforma p ON j.id_plataforma = p.id_plataforma " +
                     "JOIN genero g ON j.id_genero = g.id_genero " +
                     "WHERE j.quantidade > 0 " +
                     "ORDER BY j.titulo";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String linha = String.format(
                    "ID: %d | %s (%s) | %s | %d disponíveis | R$ %.2f/dia",
                    rs.getInt("id_jogo"),
                    rs.getString("titulo"),
                    rs.getString("plataforma"),
                    rs.getString("genero"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor_aluguel_diario")
                );
                jogos.add(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar jogos disponíveis: " + e.getMessage());
        }
        return jogos;
    }
    
    public boolean atualizarQuantidade(int idJogo, int novaQuantidade) {
        String sql = "UPDATE jogo SET quantidade = ? WHERE id_jogo = ?";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, novaQuantidade);
            pstmt.setInt(2, idJogo);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar quantidade: " + e.getMessage());
        }
        return false;
    }
    
    // DELETE
    public boolean excluir(int id) {
        String sql = "DELETE FROM jogo WHERE id_jogo = ?";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir jogo: " + e.getMessage());
        }
        return false;
    }
}