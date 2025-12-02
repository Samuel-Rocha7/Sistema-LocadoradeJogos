package model;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    
    public Cliente(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Cliente [ID=" + id + ", Nome=" + nome + ", CPF=" + cpf + "]";
    }
}
