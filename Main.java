package Locadora;

import dao.*;
import model.Cliente;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static clienteDAO clienteDAO = new clienteDAO();
    private static jogoDAO jogoDAO = new jogoDAO();
    private static aluguelDAO aluguelDAO = new aluguelDAO();
    private static viewDAO viewDAO = new viewDAO();
    private static procedureDAO procedureDAO = new procedureDAO();
    
    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("   SISTEMA LOCADORA DE JOGOS   ");
        System.out.println("===================================\n");
        
        ConexaoPostgreSQL.testarConexao();
        
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> menuClientes();
                case 2 -> menuJogos();
                case 3 -> menuAlugueis();
                case 4 -> menuViews();
                case 5 -> menuProcedures();
                case 6 -> testarTudo();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
            
        } while (opcao != 0);
        
        ConexaoPostgreSQL.fecharConexao();
        scanner.close();
    }
    
    private static void exibirMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Jogos");
        System.out.println("3. Gerenciar Aluguéis");
        System.out.println("4. Usar Views");
        System.out.println("5. Usar Procedures/Functions");
        System.out.println("6. Testar tudo");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void menuClientes() {
        System.out.println("\n=== GERENCIAR CLIENTES ===");
        System.out.println("1. Cadastrar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Buscar cliente por ID");
        System.out.println("4. Atualizar cliente");
        System.out.println("5. Excluir cliente");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> cadastrarCliente();
            case 2 -> listarClientes();
            case 3 -> buscarCliente();
            case 4 -> atualizarCliente();
            case 5 -> excluirCliente();
        }
    }
    
    private static void cadastrarCliente() {
        System.out.println("\n--- NOVO CLIENTE ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        Cliente cliente = new Cliente(nome, cpf, telefone, email);
        if (clienteDAO.inserir(cliente)) {
            System.out.println("Cliente cadastrado! ID: " + cliente.getId());
        }
    }
    
    private static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente.");
        } else {
            clientes.forEach(System.out::println);
        }
    }
    
    private static void buscarCliente() {
        System.out.print("\nID do cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente != null) {
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("Email: " + cliente.getEmail());
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    private static void atualizarCliente() {
        System.out.print("\nID do cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        System.out.print("Novo nome [" + cliente.getNome() + "]: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) cliente.setNome(nome);
        System.out.print("Novo telefone [" + cliente.getTelefone() + "]: ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) cliente.setTelefone(telefone);
        System.out.print("Novo email [" + cliente.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) cliente.setEmail(email);
        
        if (clienteDAO.atualizar(cliente)) {
            System.out.println("Cliente atualizado!");
        }
    }
    
    private static void excluirCliente() {
        System.out.print("\nID do cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Confirmar? (S/N): ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            if (clienteDAO.excluir(id)) {
                System.out.println("Cliente excluído!");
            }
        }
    }
    
    private static void menuJogos() {
        System.out.println("\n=== GERENCIAR JOGOS ===");
        System.out.println("1. Listar todos jogos");
        System.out.println("2. Listar jogos disponíveis");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> listarJogosComDetalhes();
            case 2 -> listarJogosDisponiveis();
        }
    }
    
    private static void listarJogosComDetalhes() {
        System.out.println("\n--- TODOS OS JOGOS ---");
        List<String> jogos = jogoDAO.listarComDetalhes();
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo.");
        } else {
            jogos.forEach(System.out::println);
        }
    }
    
    private static void listarJogosDisponiveis() {
        System.out.println("\n--- JOGOS DISPONÍVEIS ---");
        List<String> jogos = jogoDAO.listarDisponiveis();
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo disponível.");
        } else {
            jogos.forEach(System.out::println);
        }
    }
    
    private static void menuAlugueis() {
        System.out.println("\n=== GERENCIAR ALUGUÉIS ===");
        System.out.println("1. Realizar aluguel");
        System.out.println("2. Listar aluguéis ativos");
        System.out.println("3. Devolver jogo");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> realizarAluguel();
            case 2 -> listarAlugueisAtivos();
            case 3 -> devolverJogo();
        }
    }
    
    private static void realizarAluguel() {
        System.out.println("\n--- NOVO ALUGUEL ---");
        listarClientes();
        System.out.print("\nID do cliente: ");
        int idCliente = scanner.nextInt();
        listarJogosDisponiveis();
        System.out.print("\nID do jogo: ");
        int idJogo = scanner.nextInt();
        System.out.print("Dias: ");
        int dias = scanner.nextInt();
        
        if (aluguelDAO.realizarAluguel(idCliente, idJogo, dias)) {
            System.out.println("Aluguel realizado!");
        }
    }
    
    private static void listarAlugueisAtivos() {
        System.out.println("\n--- ALUGUÉIS ATIVOS ---");
        List<String> alugueis = aluguelDAO.listarAtivos();
        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel ativo.");
        } else {
            alugueis.forEach(System.out::println);
        }
    }
    
    private static void devolverJogo() {
        System.out.println("\n--- DEVOLVER JOGO ---");
        listarAlugueisAtivos();
        System.out.print("\nID do aluguel: ");
        int idAluguel = scanner.nextInt();
        
        if (aluguelDAO.devolverJogo(idAluguel)) {
            System.out.println("Jogo devolvido!");
        }
    }
    
    private static void menuViews() {
        System.out.println("\n=== USAR VIEWS ===");
        System.out.println("1. Ver jogos disponíveis");
        System.out.println("2. Ver jogos mais alugados");
        System.out.println("3. Ver clientes mais ativos");
        System.out.println("4. Ver aluguéis atrasados");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> exibirViewJogosDisponiveis();
            case 2 -> exibirViewJogosMaisAlugados();
            case 3 -> exibirViewClientesAtivos();
            case 4 -> exibirViewAlugueisAtrasados();
        }
    }
    
    private static void exibirViewJogosDisponiveis() {
        System.out.println("\n--- JOGOS DISPONÍVEIS ---");
        List<String> resultados = viewDAO.listarJogosDisponiveisView();
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado.");
        } else {
            resultados.forEach(System.out::println);
        }
    }
    
    private static void exibirViewJogosMaisAlugados() {
        System.out.println("\n--- JOGOS MAIS ALUGADOS ---");
        List<String> resultados = viewDAO.listarJogosMaisAlugadosView();
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado.");
        } else {
            resultados.forEach(System.out::println);
        }
    }
    
    private static void exibirViewClientesAtivos() {
        System.out.println("\n--- CLIENTES MAIS ATIVOS ---");
        List<String> resultados = viewDAO.listarClientesAtivosView();
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado.");
        } else {
            resultados.forEach(System.out::println);
        }
    }
    
    private static void exibirViewAlugueisAtrasados() {
        System.out.println("\n--- ALUGUÉIS ATRASADOS ---");
        List<String> resultados = viewDAO.listarAlugueisAtrasadosView();
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado.");
        } else {
            resultados.forEach(System.out::println);
        }
    }
    
    private static void menuProcedures() {
        System.out.println("\n=== USAR PROCEDURES ===");
        System.out.println("1. Calcular multa");
        System.out.println("2. Realizar aluguel");
        System.out.println("3. Gerar relatório");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> {
                System.out.print("ID do aluguel: ");
                int id = scanner.nextInt();
                double multa = procedureDAO.calcularMultaAtraso(id);
                System.out.println("Multa: R$ " + multa);
            }
            case 2 -> {
                System.out.print("ID do cliente: ");
                int idCliente = scanner.nextInt();
                System.out.print("ID do jogo: ");
                int idJogo = scanner.nextInt();
                System.out.print("Dias: ");
                int dias = scanner.nextInt();
                procedureDAO.realizarAluguelProcedure(idCliente, idJogo, dias);
            }
            case 3 -> {
                System.out.print("Data (YYYY-MM-DD) ou Enter: ");
                String data = scanner.nextLine();
                procedureDAO.gerarRelatorioDiario(data);
            }
        }
    }
    
    private static void testarTudo() {
        System.out.println("\n=== TESTE COMPLETO ===");
        System.out.println("1. CRUD Cliente...");
        Cliente teste = new Cliente("Teste1", "123.456.789-01", "(11) 91999-9999", "teste1@teste.com");
        clienteDAO.inserir(teste);
        
        System.out.println("2. JOINs Jogos...");
        jogoDAO.listarComDetalhes().forEach(System.out::println);
        
        System.out.println("3. Views...");
        viewDAO.listarJogosDisponiveisView();
        
        System.out.println("4. Procedures...");
        procedureDAO.gerarRelatorioDiario("");
        
        System.out.println("\nTeste completo!");
    }
}