package Locadora;

public class testeSimples {
    public static void main(String[] args) {
        System.out.println("=== TESTE SIMPLES ===");
        
        ConexaoPostgreSQL.testarConexao();
        
        System.out.println("\n--- Testando ClienteDAO ---");
        dao.clienteDAO clienteDAO = new dao.clienteDAO();
        var clientes = clienteDAO.listarTodos();
        System.out.println("Clientes no banco: " + clientes.size());
        
        System.out.println("\n--- Testando JogoDAO ---");
        dao.jogoDAO jogoDAO = new dao.jogoDAO();
        var jogos = jogoDAO.listarComDetalhes();
        System.out.println("Jogos no banco: " + jogos.size());
        
        ConexaoPostgreSQL.fecharConexao();
        System.out.println("\n✅ Teste básico concluído!");
    }
}