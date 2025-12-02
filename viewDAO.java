package dao;

import Locadora.ConexaoPostgreSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class viewDAO {
    
    public List<String> listarJogosDisponiveisView() {
        List<String> jogos = new ArrayList<>();
        String sql = "SELECT * FROM vw_jogos_disponiveis";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== JOGOS DISPONÍVEIS (VIEW) ===");
            while (rs.next()) {
                String linha = String.format(
                    "ID: %d | %s | %s | %s | %d unidades | R$ %.2f/dia",
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
            System.err.println("Erro ao usar view vw_jogos_disponiveis: " + e.getMessage());
        }
        return jogos;
    }
    
    public List<String> listarJogosMaisAlugadosView() {
        List<String> jogos = new ArrayList<>();
        String sql = "SELECT * FROM vw_jogos_mais_alugados LIMIT 5";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== JOGOS MAIS ALUGADOS (VIEW) ===");
            while (rs.next()) {
                String linha = String.format(
                    "%s (%s) | Aluguéis: %d | Receita: R$ %.2f",
                    rs.getString("titulo"),
                    rs.getString("plataforma"),
                    rs.getInt("total_alugueis"),
                    rs.getDouble("receita_total")
                );
                jogos.add(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao usar view vw_jogos_mais_alugados: " + e.getMessage());
        }
        return jogos;
    }
    
    public List<String> listarClientesAtivosView() {
        List<String> clientes = new ArrayList<>();
        String sql = "SELECT * FROM vw_clientes_ativos";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== CLIENTES MAIS ATIVOS (VIEW) ===");
            while (rs.next()) {
                String linha = String.format(
                    "%s | CPF: %s | Aluguéis: %d | Gasto total: R$ %.2f",
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getInt("total_alugueis"),
                    rs.getDouble("total_gasto")
                );
                clientes.add(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao usar view vw_clientes_ativos: " + e.getMessage());
        }
        return clientes;
    }
    
    public List<String> listarAlugueisAtrasadosView() {
        List<String> alugueis = new ArrayList<>();
        String sql = "SELECT * FROM vw_alugueis_atrasados";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== ALUGUÉIS ATRASADOS (VIEW) ===");
            while (rs.next()) {
                String linha = String.format(
                    "Aluguel ID: %d | Cliente: %s | Jogo: %s | " +
                    "Devolução prevista: %s | Dias atraso: %d",
                    rs.getInt("id_aluguel"),
                    rs.getString("cliente"),
                    rs.getString("jogo"),
                    rs.getDate("data_devolucao_prevista"),
                    rs.getInt("dias_atraso")
                );
                alugueis.add(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao usar view vw_alugueis_atrasados: " + e.getMessage());
        }
        return alugueis;
    }
}