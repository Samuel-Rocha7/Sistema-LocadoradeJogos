package dao;

import Locadora.ConexaoPostgreSQL;
import java.sql.*;

public class procedureDAO {
    
    public double calcularMultaAtraso(int idAluguel) {
        String sql = "SELECT calcular_multa_atraso(?) as multa";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idAluguel);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                double multa = rs.getDouble("multa");
                System.out.println("Multa calculada: R$ " + multa);
                return multa;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular multa: " + e.getMessage());
        }
        return 0.0;
    }
    public boolean realizarAluguelProcedure(int idCliente, int idJogo, int dias) {
        String sql = "CALL sp_realizar_aluguel(?, ?, ?)";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setInt(1, idCliente);
            cstmt.setInt(2, idJogo);
            cstmt.setInt(3, dias);
            
            cstmt.execute();
            System.out.println("✅ Aluguel realizado via procedure!");
            return true;
            
        } catch (SQLException e) {
            System.err.println("❌ Erro na procedure: " + e.getMessage());
            return false;
        }
    }
    
    public void gerarRelatorioDiario(String data) {
        String sql = "SELECT * FROM relatorio_diario(?)";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (data == null || data.isEmpty()) {
                pstmt.setDate(1, Date.valueOf(java.time.LocalDate.now()));
            } else {
                pstmt.setDate(1, Date.valueOf(data));
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("\n=== RELATÓRIO DIÁRIO ===");
                System.out.println("Total de aluguéis: " + rs.getLong("total_alugueis"));
                System.out.println("Receita total: R$ " + rs.getDouble("receita_total"));
                System.out.println("Jogos mais alugados: " + rs.getString("jogos_mais_alugados"));
                System.out.println("==========================");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
    
    public void criarProcedureTeste() {
        String sql = "CREATE OR REPLACE PROCEDURE teste_procedure() " +
                    "LANGUAGE plpgsql AS $$ " +
                    "BEGIN " +
                    "   RAISE NOTICE 'Procedure executada com sucesso!'; " +
                    "END; $$";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(sql);
            System.out.println("Procedure criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar procedure: " + e.getMessage());
        }
    }
}
