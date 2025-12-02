package dao;

import Locadora.ConexaoPostgreSQL;
import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clienteDAO {
    
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
        }
        return false;
    }
    
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY nome";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
                cliente.setId(rs.getInt("id_cliente"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }
    
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
                cliente.setId(rs.getInt("id_cliente"));
                return cliente;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }
    
    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id_cliente = ?";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setInt(5, cliente.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
        return false;
    }
    
    public boolean excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        
        try (Connection conn = ConexaoPostgreSQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
        }
        return false;
    }
}