package domain;

import java.util.List;

public class Professor extends Pessoa {

    private int cargaTrabalhoSemestre;
    private List<Area> areasDeInteresse;

    public Professor(String nome, String email) {
        super(nome, email);
    }
}
