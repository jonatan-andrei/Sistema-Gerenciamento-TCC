package domain;

public class Area {

    private Long idArea;
    private String nome;
    private String descricao;

    public Area(Long idArea, String nome, String descricao) {
        this.idArea = idArea;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getIdArea() {
        return idArea;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
