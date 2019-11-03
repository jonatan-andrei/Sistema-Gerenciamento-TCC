package domain;

import java.util.List;

public class Professor extends Pessoa {

    private int cargaTrabalhoSemestre;
    private List<Area> areasDeInteresse;
    private List<SugestaoTCC> sugestoes;

    public Professor(String nome, String email) {
        super(nome, email);
    }

    public Professor(Long idProfessor, String nome, String email) {
        super(idProfessor, nome, email);
    }

    public Professor(Long idProfessor, String nome, String email, int cargaTrabalhoSemestre) {
        super(idProfessor, nome, email);
        this.cargaTrabalhoSemestre = cargaTrabalhoSemestre;
    }

    public int getCargaTrabalhoSemestre() {
        return cargaTrabalhoSemestre;
    }

    public void setCargaTrabalhoSemestre(int cargaTrabalhoSemestre) {
        this.cargaTrabalhoSemestre = cargaTrabalhoSemestre;
    }

    public List<Area> getAreasDeInteresse() {
        return areasDeInteresse;
    }

    public void setAreasDeInteresse(List<Area> areasDeInteresse) {
        this.areasDeInteresse = areasDeInteresse;
    }

    public List<SugestaoTCC> getSugestoes() {
        return sugestoes;
    }

    public void setSugestoes(List<SugestaoTCC> sugestoes) {
        this.sugestoes = sugestoes;
    }

}
