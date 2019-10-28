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

}
