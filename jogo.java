package model;

public class jogo {
    private int id;
    private String titulo;
    private int idPlataforma;
    private int idGenero;
    private int anoLancamento;
    private String desenvolvedora;
    private int quantidade;
    private double valorAluguelDiario;

    public jogo(String titulo, int idPlataforma, int idGenero, 
                int anoLancamento, String desenvolvedora, 
                int quantidade, double valorAluguelDiario) {
        this.titulo = titulo;
        this.idPlataforma = idPlataforma;
        this.idGenero = idGenero;
        this.anoLancamento = anoLancamento;
        this.desenvolvedora = desenvolvedora;
        this.quantidade = quantidade;
        this.valorAluguelDiario = valorAluguelDiario;
    }
    
    public int getId() {
        return id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public int getIdPlataforma() {
        return idPlataforma;
    }
    
    public int getIdGenero() {
        return idGenero;
    }
    
    public int getAnoLancamento() {
        return anoLancamento;
    }
    
    public String getDesenvolvedora() {
        return desenvolvedora;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public double getValorAluguelDiario() {
        return valorAluguelDiario;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setIdPlataforma(int idPlataforma) {
        this.idPlataforma = idPlataforma;
    }
    
    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }
    
    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }
    
    public void setDesenvolvedora(String desenvolvedora) {
        this.desenvolvedora = desenvolvedora;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public void setValorAluguelDiario(double valorAluguelDiario) {
        this.valorAluguelDiario = valorAluguelDiario;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Jogo [ID=%d, Título=%s, PlataformaID=%d, GêneroID=%d, Ano=%d, Qtd=%d, R$ %.2f/dia]",
            id, titulo, idPlataforma, idGenero, anoLancamento, quantidade, valorAluguelDiario
        );
    }
    
    public String toStringFormatado() {
        return String.format(
            "ID: %d | %s | Plataforma: %d | Gênero: %d | %d unidades | R$ %.2f/dia",
            id, titulo, idPlataforma, idGenero, quantidade, valorAluguelDiario
        );
    }
}