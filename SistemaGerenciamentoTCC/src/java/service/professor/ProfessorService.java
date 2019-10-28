package service.professor;

import domain.Professor;
import java.util.List;

public interface ProfessorService {

    boolean cadastrar(String nome, String email, List<Long> areasInteresse);

    boolean editar(Long idProfessor, String nome, String email, List<Long> areasInteresse);

    List<Professor> listar();

    boolean desativar(Long idProfessor);

}
