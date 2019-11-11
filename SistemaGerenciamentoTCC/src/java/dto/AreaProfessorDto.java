package dto;

import domain.Area;

/*
    DTO utilizado para envio das informações da área e o atributo selecionado indicando se o professor tem interesse na área
 */
public class AreaProfessorDto {

    private Long idArea;
    private String nome;
    private boolean selecionado;

    public AreaProfessorDto(Area area, boolean selecionado) {
        this.idArea = area.getIdArea();
        this.nome = area.getNome();
        this.selecionado = selecionado;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
}
