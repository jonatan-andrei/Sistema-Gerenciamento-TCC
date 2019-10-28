package domain;

import java.util.List;

public class Professor extends Pessoa {

    private int cargaTrabalhoSemestre;
    private List<Area> areasDeInteresse;

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

    public void setAreasDeInteresse(List<Area> areasDeInteresse) {
        this.areasDeInteresse = areasDeInteresse;
    }
}
