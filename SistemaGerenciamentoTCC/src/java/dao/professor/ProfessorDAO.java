package dao.professor;

import domain.Professor;
import java.util.List;

public interface ProfessorDAO {

    Long cadastrar(Professor professor);

    boolean editar(Professor professor);

    List<Professor> buscar();

    boolean desativar(Long idProfessor);

}
