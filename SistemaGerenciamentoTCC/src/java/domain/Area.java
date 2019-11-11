package domain;

public class Area {

    private Long idArea;
    private String nome;

    public Area(Long idArea, String nome) {
        this.idArea = idArea;
        this.nome = nome;
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

}
